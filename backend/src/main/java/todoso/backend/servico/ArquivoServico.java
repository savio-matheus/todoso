package todoso.backend.servico;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang3.NotImplementedException;

import todoso.backend.dados.ArquivoDAO;
import todoso.backend.dados.ArquivoDTO;
import todoso.backend.dados.BdAcesso;
import todoso.backend.excecoes.NotFoundException;

public class ArquivoServico {
	private BdAcesso bancoDeDados;
	private ArquivoDAO dados;

	public ArquivoServico() throws SQLException {
		bancoDeDados = BdAcesso.abrirConexao(false);
		dados = new ArquivoDAO(bancoDeDados);
	}

	// TODO: atribuir nomes personalizados aos arquivos para evitar colisões.
	public ArquivoDTO criarArquivo(ArquivoDTO arquivo)
			throws SQLException, NoSuchAlgorithmException, IOException {
		
		long id = 0;

		try {
			arquivo.setHash(ArquivoDAO.sha256String(arquivo.getMultipartFile().getBytes()));
			id = dados.inserir(arquivo);
			bancoDeDados.fecharConexao();

			//dados.criarDiretorios(arquivo);
			//dados.escreverArquivoEmDisco(arquivo);
			
			arquivo.setId(id);
		}
		catch (SQLException e) {
			bancoDeDados.reverter();
			bancoDeDados.fecharConexao();
			throw new SQLException(e);
		}

		return arquivo;
	}

	public ArrayList<ArquivoDTO> selecionarArquivos(ArquivoDTO filtros)
			throws SQLException, Exception {

		return dados.selecionar(filtros);
	}

	public ArquivoDTO editarArquivo(ArquivoDTO arquivo) throws SQLException {
		throw new NotImplementedException("It is not possible to edit files.");
	}

	public ArquivoDTO deletarArquivo(ArquivoDTO filtros)
			throws Exception {

		try {
			ArrayList<ArquivoDTO> arquivos = dados.selecionar(filtros);
			long id = dados.excluir(filtros);

			if (id <= 0) {
				bancoDeDados.reverter();
				throw new NotFoundException("File not found. Try a different id.");
			}

			if (!arquivos.isEmpty()) {
				dados.apagarArquivoEmDisco(arquivos.get(0));
			}
		}
		catch (SQLException e) {
			bancoDeDados.reverter();
			bancoDeDados.fecharConexao();
			throw new SQLException(e);
		}

		return filtros;
	}

}