package servico;

import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import todoso.backend.dados.Tarefas;
import todoso.backend.dados.TaskDTO;
import todoso.backend.dados.BaseDTO;

/**
 *
 * @author savio
 */
public abstract class TarefaServico {
	public static ArrayList<TaskDTO> listar(TaskDTO filtros) throws SQLException {
		return Tarefas.listar(filtros);
	}
	
	public static int inserir(ArrayList<TaskDTO> tarefas) throws SQLException {
		Tarefas.inserir(tarefas);
		return 0;
	}
	
	public static int editar() {
		return 0;
	}
	
	public static int deletar(TaskDTO filtros) throws SQLException {
		Tarefas.excluir(filtros);
		return 0;
	}
}
