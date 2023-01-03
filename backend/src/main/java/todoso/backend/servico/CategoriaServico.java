package todoso.backend.servico;

import java.sql.SQLException;
import java.util.ArrayList;

import todoso.backend.dados.CategoriaDTO;
import todoso.backend.dados.Categorias;

public class CategoriaServico {

	private Categorias dados = new Categorias();

	public CategoriaDTO criarCategoria(CategoriaDTO categoria) throws SQLException {
		long id = 0;

		id = dados.inserir(categoria);
		if (id <= 0) {
			throw new SQLException("ID not found");
		}

		categoria.setId(id);
		return categoria;
	}

	public ArrayList<CategoriaDTO> selecionarCategorias(CategoriaDTO filtros) throws SQLException {
		return dados.selecionar(filtros);
	}

	public CategoriaDTO editarCategoria(CategoriaDTO categoria) throws SQLException {
		dados.atualizar(categoria);
		return categoria;
	}

	public CategoriaDTO deletarCategoria(CategoriaDTO filtros) throws SQLException {
		dados.excluir(filtros);
		return filtros;
	}
}