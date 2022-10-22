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
			bd.pstmt.setString(i++, t.getTitulo());
			bd.pstmt.setString(i++, t.getDescricao());
			bd.pstmt.setString(i++, t.getCor());
			bd.pstmt.setInt(i++, t.getPrioridade());
			bd.pstmt.setTimestamp(i++, t.getDataCriacao());
			bd.pstmt.setTimestamp(i++, t.getDataConcluida());
			bd.pstmt.setTimestamp(i++, t.getDataLimite());

			bd.pstmt.addBatch();
		}
		bd.pstmt.executeBatch();
	
		bd.fecharConexao();
		return alteracoes;
	}
	
	public static ArrayList<TaskDTO> listar(TaskDTO filtros) throws SQLException {

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

		if (filtros.getTitulo() != null) {
			bd.pstmt.setString(i++, "%" + filtros.getTitulo() + "%");
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getDescricao() != null) {
			bd.pstmt.setString(i++, "%" + filtros.getDescricao() + "%");
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getCor() != null) {
			bd.pstmt.setString(i++, filtros.getCor());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getPrioridade() != null) {
			bd.pstmt.setString(i++, filtros.getPrioridade().toString());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getDataCriacao() != null) {
			bd.pstmt.setTimestamp(i++, filtros.getDataCriacao());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getDataConcluida() != null) {
			bd.pstmt.setTimestamp(i++, filtros.getDataConcluida());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getDataLimite() != null) {
			bd.pstmt.setTimestamp(i++, filtros.getDataLimite());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (filtros.getLimite() != null) {
			bd.pstmt.setLong(i++, filtros.getLimite());
		} else {
			bd.pstmt.setLong(i++, Long.MAX_VALUE);
		}

		if (filtros.getOffset() != null) {
			bd.pstmt.setLong(i++, filtros.getOffset());
		} else {
			bd.pstmt.setLong(i++, 0);
		}

		//System.out.println(bd.pstmt.toString());

		bd.rs = bd.pstmt.executeQuery();
		
		ArrayList<TaskDTO> tarefas = new ArrayList<>();
		
		while (bd.rs.next()) {
			TaskDTO t = new TaskDTO();
			
			t.setId(bd.rs.getLong("id"));
			t.setTitulo(bd.rs.getString("titulo"));
			t.setDescricao(bd.rs.getString("descricao"));
			t.setDataCriacao(bd.rs.getTimestamp("data_criacao"));
			t.setDataConcluida(bd.rs.getTimestamp("data_concluida"));
			t.setDataLimite(bd.rs.getTimestamp("data_limite"));
			t.setPrioridade(bd.rs.getInt("prioridade"));
			t.setCor(bd.rs.getString("cor"));
			
			tarefas.add(t);
		}
		
		return tarefas;
	}

	public static int atualizar(TaskDTO tarefa) {
		ArrayList<TaskDTO> l = new ArrayList<>();
		return atualizar(l);
	}
	
	public static int atualizar(ArrayList<TaskDTO> tarefas) {
		return 0;
	}

	/*public static int atualizar(TaskDTO filtro, TaskDTO alteracoes) {
		return 0;
	}*/

	public static int excluir(TaskDTO filtros) throws SQLException {
		String sql = "DELETE FROM tarefas WHERE id = ?";
		int i = 1;
		
		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql);
		
		bd.pstmt.setLong(i++, filtros.getId());
		if (bd.pstmt.execute()) {
			bd.fecharConexao();
			return 1;
		} else {
			bd.fecharConexao();
			return 0;
		}
	}

	protected static ArrayList<TaskDTO> criaLista(TaskDTO tarefa) {
		ArrayList<TaskDTO> l = new ArrayList<>();
		l.add(tarefa);
		return l;
	}
}
