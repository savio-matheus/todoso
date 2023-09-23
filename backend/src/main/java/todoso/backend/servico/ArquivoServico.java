package todoso.backend.servico;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang3.NotImplementedException;

import todoso.backend.dados.ArquivoDAO;
import todoso.backend.dados.ArquivoDTO;
import todoso.backend.excecoes.NotFoundException;

public class ArquivoServico {
	private ArquivoDAO dados = new ArquivoDAO();

	// TODO: atribuir nomes personalizados aos arquivos para evitar colisões.
	public ArquivoDTO criarArquivo(ArquivoDTO arquivo) throws SQLException {
		long id = 0;

		id = dados.inserir(arquivo);
		//dados.criarDiretorios(arquivo);
		//dados.escreverArquivoEmDisco(arquivo);

		if (id <= 0) {
			throw new SQLException("Resource was created, but returned no valid id.");
		}

		arquivo.setId(id);

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

		ArrayList<ArquivoDTO> arquivos = dados.selecionar(filtros);
		long id = dados.excluir(filtros);

		if (id <= 0) {
			throw new NotFoundException("File not found. Try a different id.");
		}

		if (!arquivos.isEmpty()) {
			dados.apagarArquivoEmDisco(arquivos.get(0));
		}

		return filtros;
	}

}