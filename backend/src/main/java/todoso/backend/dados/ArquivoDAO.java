package todoso.backend.dados;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class ArquivoDAO implements BaseDAO {

	//@Value("${arquivos.diretorioRaiz}") TODO: criar classe de configurações
	private String DIRETORIO_RAIZ = "/arquivos-todoso";

	@Override
	public long inserir(BaseDTO dto) throws SQLException {
		String sql =
			"INSERT INTO arquivos (url, mime, tamanho)\n" +
			"VALUES (?, ?, ?);";

		BdAcesso bd = BdAcesso.abrirConexao(false);
		bd.pstmt = bd.conexao.prepareStatement(sql, bd.RETURN_GENERATED_KEYS);

		ArquivoDTO a = (ArquivoDTO) dto;
		int i = 1;
		long idGerado = 0;
		// TODO: atualizar quando for implementado suporte multiusuário.
		Path caminhoCompleto = Paths.get(DIRETORIO_RAIZ + a.getNome());

		bd.pstmt.setString(i++, caminhoCompleto.toString());
		bd.pstmt.setString(i++, a.getMimetype());
		bd.pstmt.setLong(i++, a.getTamanho());
		bd.pstmt.execute();

		idGerado = bd.getChaveGerada();

		if (idGerado <= 0) {
			bd.fecharConexao();
			return idGerado;
		}

		try {
			// TODO: essas funções deveriam ser chamadas na classe de serviço
			criarDiretorios(a);
			escreverArquivoEmDisco(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bd.conexao.rollback();
			e.printStackTrace();
			throw new SQLException("File not saved!");
		}

		bd.fecharConexao();

		return idGerado;
	}

	@Override
	public ArrayList<ArquivoDTO> selecionar(BaseDTO filtros) throws SQLException, Exception {
		ArquivoDTO arqDTO = (ArquivoDTO) filtros;

		final String sql = "SELECT * FROM arquivos a WHERE a.id = ?;";

		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql);
		bd.pstmt.setLong(1, arqDTO.getId());

		bd.rs = bd.pstmt.executeQuery();

		ArrayList<ArquivoDTO> arquivos = new ArrayList<>();
		ArquivoDTO ar;
		while (bd.rs.next()) {
			String url = bd.rs.getString("url");
			MultipartFile mpf = lerArquivoEmDisco(url);
			ar = new ArquivoDTO();
			ar.setMultipartFile(mpf);
			arquivos.add(ar);
			System.out.println(ar.toString());
		}

		bd.fecharConexao();
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

		BdAcesso bd = BdAcesso.abrirConexao(false);
		bd.pstmt = bd.conexao.prepareStatement(sql);

		bd.pstmt.setLong(1, a.getId());
		bd.pstmt.execute();
		bd.fecharConexao();

		return a.getId();
	}

	public MultipartFile lerArquivoEmDisco(String url) throws IOException {
		File arquivoDisco = new File(url);
		MultipartFileImpl mpf = new MultipartFileImpl(arquivoDisco);
		return mpf;
	}

	public String escreverArquivoEmDisco(ArquivoDTO arquivo) throws IOException {
		MultipartFile mpf = arquivo.getMultipartFile();
		File arquivoDisco = new File(DIRETORIO_RAIZ + arquivo.getNome());
		arquivoDisco.createNewFile();
		Files. write(
			arquivoDisco.toPath(),
			mpf.getBytes(),
			StandardOpenOption.TRUNCATE_EXISTING
		);
		return arquivoDisco.toPath().toString();
	}

	public String apagarArquivoEmDisco(ArquivoDTO arquivo) throws IOException {
		File arquivoDisco = new File(DIRETORIO_RAIZ + arquivo.getNome());
		if (!arquivoDisco.exists()) {
			throw new IOException("File does not exist.");
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
}
