package todoso.backend.servico;

import java.sql.SQLException;
import java.util.ArrayList;

import todoso.backend.excecoes.NotFoundException;
import todoso.backend.dados.Tarefas;
import todoso.backend.dados.TarefaDTO;

// TODO: retornar tarefas com IDs
public class TarefaServico {
	private Tarefas dados = new Tarefas();

	public TarefaDTO criarTarefa(TarefaDTO tarefa) throws SQLException {
		long id;
		id = dados.inserir(tarefa);
		if (id <= 0) {
			throw new SQLException("Resource was created, but returned no valid id.");
		}
		tarefa.setId(id);
		return tarefa;
	}

	public ArrayList<TarefaDTO> selecionarTarefas(TarefaDTO filtros) throws SQLException {

		return dados.selecionar(filtros);
	}

	public TarefaDTO editarTarefa(TarefaDTO tarefaNova)
		throws SQLException, IllegalArgumentException, NotFoundException {

		if (tarefaNova.getId() == null) {
			throw new IllegalArgumentException("Id must not be null.");
		}

		long id = dados.atualizar(tarefaNova);

		if (id <= 0) {
			throw new NotFoundException("Try a different id.");
		}

		return tarefaNova;
	}

	public TarefaDTO deletarTarefa(TarefaDTO filtros)
		throws SQLException, IllegalArgumentException, NotFoundException {

		dados.excluir(filtros);

		return filtros;
	}
}