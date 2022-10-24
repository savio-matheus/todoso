package servico;

import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import todoso.backend.dados.Tarefas;
import todoso.backend.dados.TarefaDTO;
import todoso.backend.dados.BaseDTO;

/**
 *
 * @author savio
 */
public abstract class TarefaServico {
	public static ArrayList<TarefaDTO> listar(TarefaDTO filtros) throws SQLException {
		return new Tarefas().listar(filtros);
	}
	
	public static int inserir(ArrayList<TarefaDTO> tarefas) throws SQLException {
		return new Tarefas().inserir(tarefas);
	}
	
	public static int editar(ArrayList<TarefaDTO> tarefas) throws SQLException {
		return new Tarefas().atualizar(tarefas);
	}
	
	public static int deletar(TarefaDTO filtros) throws SQLException {
		return new Tarefas().excluir(filtros);
	}
}
