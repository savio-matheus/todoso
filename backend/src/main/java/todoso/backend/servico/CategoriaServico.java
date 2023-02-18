package todoso.backend.servico;

import java.lang.IllegalArgumentException;
import java.sql.SQLException;
import java.util.ArrayList;

import todoso.backend.dados.CategoriaDTO;
import todoso.backend.dados.CategoriaDAO;
import todoso.backend.excecoes.NotFoundException;

public class CategoriaServico {

	private CategoriaDAO dados = new CategoriaDAO();

	public CategoriaDTO criarCategoria(CategoriaDTO categoria) throws SQLException {
		long id = 0;

		id = dados.inserir(categoria);
		if (id <= 0) {
			throw new SQLException("Resource was created, but returned no valid id.");
		}

		categoria.setId(id);
		return categoria;
	}

	public ArrayList<CategoriaDTO> selecionarCategorias(CategoriaDTO filtros) throws SQLException {
		return dados.selecionar(filtros);
	}

	public CategoriaDTO editarCategoria(CategoriaDTO categoria)
		throws SQLException, IllegalArgumentException, NotFoundException {

		if (categoria.getId() == null) {
			throw new IllegalArgumentException("Id must not be null.");
		}

		long id = dados.atualizar(categoria);

		if (id <= 0) {
			throw new NotFoundException("Try a different id.");
		}
		return categoria;
	}

	public CategoriaDTO deletarCategoria(CategoriaDTO filtros)
		throws SQLException, NotFoundException, IllegalArgumentException {
		if (filtros.getId() == null) {
			throw new IllegalArgumentException("Id must not be null.");
		}
		long id = dados.excluir(filtros);

		if (id <= 0) {
			throw new NotFoundException("Try a different id.");
		}
		return filtros;
	}
}