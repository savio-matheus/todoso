package todoso.backend.dados;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class ArquivoDAO implements BaseDAO {
	private BdAcesso bd = null;

	public ArquivoDAO(BdAcesso bd) throws SQLException {
		if (bd != null && bd.conexao != null && !bd.conexao.isClosed()) {
			this.bd = bd;
		}
		else {
			throw new SQLException("Não foi fornecida uma conexão válida " +
				"com o banco de dados.");
		}
	}

	//@Value("${arquivos.diretorioRaiz}") TODO: criar classe de configurações
	private String DIRETORIO_RAIZ = "./arquivos-todoso/";

	@Override
	public long inserir(BaseDTO dto) throws SQLException {
		String sql =
			"INSERT INTO arquivos (url, mime, tamanho, nome_original, sha256)\n" +
			"VALUES (?, ?, ?, ?, ?);";

		bd.pstmt = bd.conexao.prepareStatement(sql, bd.RETURN_GENERATED_KEYS);

		ArquivoDTO a = (ArquivoDTO) dto;
		int i = 1;
		long idGerado = 0;
		// TODO: atualizar quando for implementado suporte multiusuário.
		Path caminhoCompleto = Paths.get(DIRETORIO_RAIZ + a.getNome());

		bd.pstmt.setString(i++, caminhoCompleto.toString());
		bd.pstmt.setString(i++, a.getMimetype());
		bd.pstmt.setLong(i++, a.getTamanho());
		bd.pstmt.setString(i++, a.getNome());
		bd.pstmt.setString(i++, a.getHash());
		bd.pstmt.execute();

		idGerado = bd.getChaveGerada();

		try {
			// TODO: essas funções deveriam ser chamadas na classe de serviço
			criarDiretorios(a);
			escreverArquivoEmDisco(a);
		} catch (Exception e) {
			bd.reverter();
			e.printStackTrace();
			throw new SQLException("File not saved!");
		}

		return idGerado;
	}

	@Override
	public ArrayList<ArquivoDTO> selecionar(BaseDTO filtros) throws SQLException, Exception {
		ArquivoDTO arqDTO = (ArquivoDTO) filtros;

		final String sql = "SELECT * FROM arquivos a WHERE a.id = ?;";

		bd.pstmt = bd.conexao.prepareStatement(sql);
		bd.pstmt.setLong(1, arqDTO.getId());

		bd.rs = bd.pstmt.executeQuery();

		ArrayList<ArquivoDTO> arquivos = new ArrayList<>();
		ArquivoDTO arq;
		while (bd.rs.next()) {
			String url = bd.rs.getString("url");
			MultipartFile mpf = lerArquivoEmDisco(url);
			arq = new ArquivoDTO();
			arq.setMultipartFile(mpf);
			arquivos.add(arq);
		}

		bd.pstmt.close();
		return arquivos;
	}

	@Override
	public long atualizar(BaseDTO dto) throws SQLException {
		throw new UnsupportedOperationException(
			"Não é possível atualizar um arquivo anexo.");
	}

	@Override
	public long excluir(BaseDTO filtros) throws SQLException {
		final String sql = "DELETE FROM arquivos WHERE id = ?";
		ArquivoDTO a = (ArquivoDTO) filtros;

		bd.pstmt = bd.conexao.prepareStatement(sql);

		bd.pstmt.setLong(1, a.getId());
		boolean apagado = bd.pstmt.execute();
		bd.pstmt.close();

		return (apagado) ? a.getId() : -1;
	}

	// Todos os métodos abaixo pressupõem que o caminho da url é válido.

	public MultipartFile lerArquivoEmDisco(String url) throws IOException {
		File arquivoDisco = new File(url);
		MultipartFileImpl mpf = new MultipartFileImpl(arquivoDisco);
		return mpf;
	}

	public String escreverArquivoEmDisco(ArquivoDTO arquivo) throws IOException {
		File arquivoDisco = new File(DIRETORIO_RAIZ + arquivo.getNome());
		arquivoDisco.createNewFile();
		Files.write(
			arquivoDisco.toPath(),
			arquivo.getMultipartFile().getBytes()
		);
		return arquivoDisco.toPath().toString();
	}

	public String apagarArquivoEmDisco(ArquivoDTO arquivo) throws IOException {
		File arquivoDisco = new File(DIRETORIO_RAIZ + arquivo.getNome());

		if (!arquivoDisco.exists()) {
			return DIRETORIO_RAIZ + arquivo.getNome();
		}

		arquivoDisco.delete();
		return arquivoDisco.getPath().toString();
	}

	public boolean criarDiretorios(ArquivoDTO arquivo) throws Exception {
		System.out.println(DIRETORIO_RAIZ);
		File f = new File(DIRETORIO_RAIZ);
		if (f.isDirectory()) { return true; }
		return f.mkdirs();
	}

	public static String sha256String(byte[] conteudo) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hash = md.digest(conteudo);

		BigInteger n = new BigInteger(1, hash);
		StringBuffer resultado = new StringBuffer(n.toString(16));
		while (resultado.length() < 64) {
			resultado.insert(0, "0");
		}

		return resultado.toString();
	}
}
