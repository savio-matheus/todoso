package todoso.backend.dados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArquivoDAO implements BaseDAO {
	// TODO: esta informação deve ser passada como propriedade em runtime!!!
	private final String PATH_ARQUIVOS = ".\\anexos-todoso\\";

	@Override
	public long inserir(BaseDTO dto) throws SQLException {
		String sql =
			"INSERT INTO arquivos (url, mime, tamanho)\n" +
			"VALUES (?, ?, ?);";
		
		// TODO: tudo deveria ser feito dentro de uma transaction pois a gravação pode falhar.
		BdAcesso bd = BdAcesso.abrirConexao();
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
			System.out.println(caminhoCompleto.toString());
			File arquivoDisco = new File(caminhoCompleto.toString());
			if (!arquivoDisco.createNewFile()) {
				System.out.println("File already exists! It'll be overwritten.");
			}
			Files.copy(
				a.getMultipartFile().getInputStream(),
				caminhoCompleto,
				StandardCopyOption.REPLACE_EXISTING
			);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SQLException("File not saved!");
		}

		bd.fecharConexao();

		return idGerado;
	}

	@Override
	public ArrayList<? extends BaseDTO> selecionar(BaseDTO filtros) throws SQLException {
		return null;
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
	
}
