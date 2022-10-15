package todoso.backend.dados;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author savio
 */
public class Tarefas {

	public static int inserir(TaskDTO tarefa) throws SQLException {
		return inserir(criaLista(tarefa));
	}
	
	public static int inserir(ArrayList<TaskDTO> tarefas) throws SQLException {
		
		String sql = 
			"INSERT INTO tarefas (\n" +
			"	titulo,\n" +
			"	descricao,\n" +
			"	cor,\n" +
			"	prioridade,\n" +
			"	data_criacao,\n" +
			"	data_concluida,\n" +
			"	data_limite)\n" +
			"VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		int alteracoes = 0;
		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pStatement = bd.conexao.prepareStatement(sql);
		
		for (TaskDTO t : tarefas) {
			int i = 1;
			bd.pStatement.setString(i++, t.getTitle());
			bd.pStatement.setString(i++, t.getDescription());
			bd.pStatement.setString(i++, t.getColor());
			bd.pStatement.setInt(i++, t.getPriority());
			bd.pStatement.setTimestamp(i++, t.getCreationDate());
			bd.pStatement.setTimestamp(i++, t.getCompletionDate());
			bd.pStatement.setTimestamp(i++, t.getDeadline());

			bd.pStatement.addBatch();
		}
		bd.pStatement.executeBatch();
	
		bd.fecharConexao();
		return alteracoes;
	}
	
	public static ArrayList<TaskDTO> listar(TaskDTO filtros, Integer limit,
			Integer offset) throws SQLException {

		String sql =
			"";
		
		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pStatement = bd.conexao.prepareStatement(sql);
		
		return null;
	}

	public static int atualizar(TaskDTO tarefa) {
		return 0;
	}
	
	public static int atualizar(ArrayList<TaskDTO> tarefas) {
		return 0;
	}

	public static int atualizar(TaskDTO filtro, TaskDTO alteracoes, Integer limit) {
		return 0;
	}

	public static int excluir(TaskDTO filtros, Integer limit, Integer offset) {
		return 0;
	}

	protected static ArrayList<TaskDTO> criaLista(TaskDTO tarefa) {
		ArrayList<TaskDTO> l = new ArrayList<>();
		l.add(tarefa);
		return l;
	}
}
