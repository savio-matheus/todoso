package todoso.backend.servico;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import todoso.backend.excecoes.NotFoundException;
import todoso.backend.dados.TarefasDAO;
import todoso.backend.dados.CategoriaDAO;
import todoso.backend.dados.CategoriaDTO;
import todoso.backend.dados.TarefaDTO;

// TODO: retornar tarefas com IDs
public class TarefaServico {
	private TarefasDAO dados = new TarefasDAO();
	final long ID_CATEGORIA_PADRAO = 1;

	public TarefaDTO criarTarefa(TarefaDTO tarefa) throws SQLException {
		long id;
		id = dados.inserir(tarefa);

		tarefa.setId(id);

		if (tarefa.getCategorias() == null) {
			ArrayList<CategoriaDTO> l = new ArrayList<>();
			CategoriaDTO padrao = new CategoriaDTO();
			padrao.setId(ID_CATEGORIA_PADRAO);
			l.add(padrao);

			tarefa.setCategorias(l);
		}

		CategoriaDAO catDAO = new CategoriaDAO();
		for (CategoriaDTO c : tarefa.getCategorias()) {
			// TODO: problema: o que fazer se, dentre N operações, uma falhar? Usar transações?
			if (!catDAO.relacionarTarefaCategoria(tarefa, c)) {
				throw new SQLException("Failed to assign task "
					+ tarefa.getId() + " category " + c.getId() + ".");
			}
		}

		if (id <= 0) {
			throw new SQLException("Resource was created, but returned no valid id.");
		}
		tarefa.setId(id);
		return tarefa;
	}

	public ArrayList<TarefaDTO> selecionarTarefas(TarefaDTO filtros) throws SQLException {

		ArrayList<TarefaDTO> resultados = dados.selecionar(filtros);
		HashMap<Long, ArrayList<CategoriaDTO>> categorias = new CategoriaDAO()
			.selecionarCategoriasPorTarefa(resultados);

		for (TarefaDTO t : resultados) {
			t.setCategorias(categorias.get(t.getId()));
		}

		return resultados;
	}

	public TarefaDTO editarTarefa(TarefaDTO tarefaNova)
		throws SQLException, IllegalArgumentException, NotFoundException {

		if (tarefaNova.getId() == null) {
			throw new IllegalArgumentException("Id cannot be null.");
		}

		long id = dados.atualizar(tarefaNova);

		if (id <= 0) {
			throw new NotFoundException("Try a different id.");
		}

		// Retiramos todas as categorias da tarefa do banco e adicionamos
		// novamente para refletir a tarefa editada.
		// TODO: talvez tenha forma melhor de fazer isso

		if (tarefaNova.getCategorias() == null || tarefaNova.getCategorias().size() == 0) {
			ArrayList<CategoriaDTO> l = new ArrayList<>();
			CategoriaDTO c = new CategoriaDTO();
			c.setId(ID_CATEGORIA_PADRAO);
			l.add(c);
			tarefaNova.setCategorias(l);
		}

		CategoriaDAO catDAO = new CategoriaDAO();
		catDAO.desfazerRelacaoTarefaCategoria(tarefaNova, null);

		for (CategoriaDTO c : tarefaNova.getCategorias()) {
			catDAO.relacionarTarefaCategoria(tarefaNova, c);
		}

		return tarefaNova;
	}

	public TarefaDTO deletarTarefa(TarefaDTO filtros)
		throws SQLException, IllegalArgumentException, NotFoundException {

		ArrayList<TarefaDTO> retorno = this.selecionarTarefas(filtros);

		if (retorno.size() == 0) {
			throw new NotFoundException("Id not found. Try a different one.");
		}

		for (CategoriaDTO c : retorno.get(0).getCategorias()) {
			CategoriaDAO catDAO = new CategoriaDAO();

			catDAO.desfazerRelacaoTarefaCategoria(filtros, c);
		}

		long id = dados.excluir(filtros);
		filtros.setId(id);

		return filtros;
	}
}