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
		dados.inserir(dados.criarLista(tarefa));
		return tarefa;
	}

	public ArrayList<TarefaDTO> selecionarTarefas(TarefaDTO filtros) throws SQLException {

		return dados.listar(filtros);
	}

	public TarefaDTO editarTarefa(TarefaDTO tarefaNova)
		throws SQLException, IllegalArgumentException, NotFoundException {

		dados.atualizar(tarefaNova);

		return tarefaNova;
	}

	public TarefaDTO deletarTarefa(TarefaDTO filtros)
		throws SQLException, IllegalArgumentException, NotFoundException {

		dados.excluir(filtros);

		return filtros;
	}
}