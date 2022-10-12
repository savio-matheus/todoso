package todoso.backend.dados;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author savio
 */
public class Tarefas {
	
	private ArrayList<TaskDTO> tarefas;
	
	public Tarefas(TaskDTO tarefa) {
		tarefas = new ArrayList<>();
		tarefas.add(tarefa);
	}
	
	public Tarefas(ArrayList<TaskDTO> tarefas) {
		this.tarefas = tarefas;
	}

	private int inserir(ArrayList<TaskDTO> objetos) throws SQLException {
		
		String sql;
		
		int alteracoes = 0;
		BdAcesso bd = BdAcesso.abrirConexao();
		bd.fecharConexao();
		return alteracoes;
	}

	private int editar(ArrayList<TaskDTO> objetos) {
		return 0;
	}

	private int editar(TaskDTO filtro, TaskDTO alteracoes, Integer limit) {
		return 0;
	}

	private int listar(TaskDTO filtros, Integer limit, Integer offset) {
		return 0;
	}

	private int excluir(TaskDTO filtros, Integer limit, Integer offset) {
		return 0;
	}

}
