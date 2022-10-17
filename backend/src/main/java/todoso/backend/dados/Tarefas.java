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
		bd.pstmt = bd.conexao.prepareStatement(sql);
		
		for (TaskDTO t : tarefas) {
			int i = 1;
			bd.pstmt.setString(i++, t.getTitle());
			bd.pstmt.setString(i++, t.getDescription());
			bd.pstmt.setString(i++, t.getColor());
			bd.pstmt.setInt(i++, t.getPriority());
			bd.pstmt.setTimestamp(i++, t.getCreationDate());
			bd.pstmt.setTimestamp(i++, t.getCompletionDate());
			bd.pstmt.setTimestamp(i++, t.getDeadline());

			bd.pstmt.addBatch();
		}
		bd.pstmt.executeBatch();
	
		bd.fecharConexao();
		return alteracoes;
	}
	
	public static ArrayList<TaskDTO> listar(TaskDTO filtros, Long limit,
			Long offset) throws SQLException {

		String sql =
			"SELECT * FROM tarefas t WHERE\n" +
			"	TRUE\n" +
			"	AND id LIKE ?\n" +
			"	AND titulo LIKE ?\n" +
			"	AND descricao LIKE ?\n" +
			"	AND cor LIKE ?\n" +
			"	AND prioridade LIKE ?\n" +
			"	AND data_criacao LIKE ?\n" +
			"	AND data_concluida LIKE ?\n" +
			"	AND data_limite LIKE ?\n" +
			"ORDER BY t.id\n" +
			"LIMIT ? OFFSET ?;";
		
		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql);
		int i = 1;
		if (filtros.getId() != null) {
			bd.pstmt.setString(i++, filtros.getId().toString());
		}
		else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getTitle() != null) {
			bd.pstmt.setString(i++, "%" + filtros.getTitle() + "%");
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getDescription() != null) {
			bd.pstmt.setString(i++, "%" + filtros.getDescription() + "%");
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getColor() != null) {
			bd.pstmt.setString(i++, filtros.getColor());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getPriority() != null) {
			bd.pstmt.setString(i++, filtros.getPriority().toString());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getCreationDate() != null) {
			bd.pstmt.setTimestamp(i++, filtros.getCreationDate());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getCompletionDate() != null) {
			bd.pstmt.setTimestamp(i++, filtros.getCompletionDate());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getDeadline() != null) {
			bd.pstmt.setTimestamp(i++, filtros.getDeadline());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (limit != null) {
			bd.pstmt.setLong(i++, limit);
		} else {
			bd.pstmt.setLong(i++, Long.MAX_VALUE);
		}

		if (offset != null) {
			bd.pstmt.setLong(i++, offset);
		} else {
			bd.pstmt.setLong(i++, 0);
		}

		//System.out.println(bd.pstmt.toString());

		bd.rs = bd.pstmt.executeQuery();
		
		ArrayList<TaskDTO> tarefas = new ArrayList<>();
		
		while (bd.rs.next()) {
			TaskDTO t = new TaskDTO();
			
			t.setId(bd.rs.getLong("id"));
			t.setTitle(bd.rs.getString("titulo"));
			t.setDescription(bd.rs.getString("descricao"));
			t.setCreationDate(bd.rs.getTimestamp("data_criacao"));
			t.setCompletionDate(bd.rs.getTimestamp("data_concluida"));
			t.setDeadline(bd.rs.getTimestamp("data_limite"));
			t.setPriority(bd.rs.getInt("prioridade"));
			t.setColor(bd.rs.getString("cor"));
			
			tarefas.add(t);
		}
		
		return tarefas;
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
