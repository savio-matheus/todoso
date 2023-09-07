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

import org.springframework.web.multipart.MultipartFile;

public class ArquivoDAO implements BaseDAO {
	// TODO: esta informação deve ser passada como propriedade em runtime!!!
	private final String PATH_ARQUIVOS = "./anexos-todoso/";

	@Override
	public long inserir(BaseDTO dto) throws SQLException {
		String sql =
			"INSERT INTO arquivos (url, mime, tamanho)\n" +
			"VALUES (?, ?, ?);";

		// TODO: tudo deveria ser feito dentro de uma transaction pois a gravação pode falhar.
		BdAcesso bd = BdAcesso.abrirConexao(false);
		bd.pstmt = bd.conexao.prepareStatement(sql, bd.RETURN_GENERATED_KEYS);

		ArquivoDTO a = (ArquivoDTO) dto;
		int i = 1;
		long idGerado = 0;
		// TODO: atualizar quando for implementado suporte multiusuário.
		Path caminhoCompleto = Paths.get(PATH_ARQUIVOS + a.getNome());

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
			// TODO: o que fazer quando o arquivo já existe?
			// TODO: criar também a pasta "anexos-todoso"
			escreverArquivo(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			bd.conexao.rollback();
			e.printStackTrace();
			throw new SQLException("File not saved!");
		}

		bd.fecharConexao();

		return idGerado;
	}

	@Override
	public ArrayList<? extends BaseDTO> selecionar(BaseDTO filtros) throws SQLException, Exception {
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
			MultipartFile mpf = lerArquivo(url);
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
		return 0;
	}

	public MultipartFile lerArquivo(String url) throws IOException {
		File arquivoDisco = new File(url);
		MultipartFileImpl mpf = new MultipartFileImpl(arquivoDisco);
		return mpf;
	}

	public String escreverArquivo(ArquivoDTO arquivo) throws IOException {
		MultipartFile mpf = arquivo.getMultipartFile();
		File arquivoDisco = new File(PATH_ARQUIVOS + arquivo.getNome());
		arquivoDisco.createNewFile();
		Files. write(
			arquivoDisco.toPath(),
			mpf.getBytes(),
			StandardOpenOption.TRUNCATE_EXISTING
		);
		return arquivoDisco.toPath().toString();
	}

}
