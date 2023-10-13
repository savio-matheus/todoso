package todoso.backend.servico;

import java.lang.IllegalArgumentException;
import java.sql.SQLException;
import java.util.ArrayList;

import todoso.backend.dados.CategoriaDTO;
import todoso.backend.dados.BdAcesso;
import todoso.backend.dados.CategoriaDAO;
import todoso.backend.excecoes.NotFoundException;

public class CategoriaServico {

	private BdAcesso bancoDeDados;
	private CategoriaDAO dados;

	public CategoriaServico() throws SQLException {
		bancoDeDados = BdAcesso.abrirConexao(false);
		dados = new CategoriaDAO(bancoDeDados);
	}

	public CategoriaDTO criarCategoria(CategoriaDTO categoria) throws SQLException {
		long id = 0;

		id = dados.inserir(categoria);
		if (id <= 0) {
			bancoDeDados.reverter();
			throw new SQLException("Nenhum id válido foi retornado.");
		}

		categoria.setId(id);
		bancoDeDados.close();
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
			bancoDeDados.reverter();
			throw new NotFoundException("Try a different id.");
		}

		bancoDeDados.close();
		return categoria;
	}

	public CategoriaDTO deletarCategoria(CategoriaDTO filtros)
		throws SQLException, NotFoundException, IllegalArgumentException {
		if (filtros.getId() == null) {
			throw new IllegalArgumentException("Id must not be null.");
		}
		if (filtros.getId() == 1) {
			throw new SQLException("Cannot delete default category.");
		}

		// TODO: sei não
		dados.desfazerRelacaoTarefaCategoria(null, filtros);
		dados.relacionarCategoriaPadrao();
		long id = dados.excluir(filtros);

		if (id <= 0) {
			bancoDeDados.reverter();
			throw new NotFoundException("Try a different id.");
		}

		bancoDeDados.close();
		return filtros;
	}
}