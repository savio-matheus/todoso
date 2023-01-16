package todoso.backend.dados;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author savio
 */

public class Tarefas implements Dados {

	public long inserir(BaseDTO dto) throws SQLException {
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

		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql, bd.RETURN_GENERATED_KEYS);
		TarefaDTO tarefa = (TarefaDTO) dto;

		int i = 1;
		bd.pstmt.setString(i++, tarefa.getTitulo());
		bd.pstmt.setString(i++, tarefa.getDescricao());
		bd.pstmt.setString(i++, tarefa.getCor());
		bd.pstmt.setInt(i++, tarefa.getPrioridade());
		bd.pstmt.setTimestamp(i++, tarefa.getDataCriacao());
		bd.pstmt.setTimestamp(i++, tarefa.getDataConcluida());
		bd.pstmt.setTimestamp(i++, tarefa.getDataLimite());

		bd.pstmt.execute();
		long idGerado = bd.getChaveGerada();
		bd.fecharConexao();

		return idGerado;
	}

	public ArrayList<TarefaDTO> selecionar(BaseDTO filtros) throws SQLException {
		String sql =
			"SELECT * FROM tarefas t WHERE\n" +
			"	TRUE\n" +
			"	AND (id LIKE ? OR id IS NULL)\n" +
			"	AND (titulo LIKE ? OR titulo IS NULL)\n" +
			"	AND (descricao LIKE ? OR descricao IS NULL)\n" +
			"	AND (cor LIKE ? OR cor IS NULL)\n" +
			"	AND (prioridade LIKE ? OR prioridade IS NULL)\n" +
			"	AND (data_criacao LIKE ? OR data_criacao IS NULL)\n" +
			"	AND (data_concluida LIKE ? OR data_concluida IS NULL)\n" +
			"	AND (data_limite LIKE ? OR data_limite IS NULL)\n" +
			"ORDER BY t.id\n" +
			"LIMIT ? OFFSET ?;";

		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql);
		TarefaDTO f = (TarefaDTO) filtros;
		int i = 1;
		if (f.getId() != null) {
			bd.pstmt.setString(i++, f.getId().toString());
		}
		else {
			bd.pstmt.setString(i++, "%");
		}

		if (f.getTitulo() != null) {
			bd.pstmt.setString(i++, "%" + f.getTitulo() + "%");
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (f.getDescricao() != null) {
			bd.pstmt.setString(i++, "%" + f.getDescricao() + "%");
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (f.getCor() != null) {
			bd.pstmt.setString(i++, f.getCor());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (f.getPrioridade() != null) {
			bd.pstmt.setString(i++, f.getPrioridade().toString());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (f.getDataCriacao() != null) {
			bd.pstmt.setTimestamp(i++, f.getDataCriacao());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (f.getDataConcluida() != null) {
			bd.pstmt.setTimestamp(i++, f.getDataConcluida());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (f.getDataLimite() != null) {
			bd.pstmt.setTimestamp(i++, f.getDataLimite());
		} else {
			bd.pstmt.setString(i++, "%");
		}

		if (f.getLimite() != null) {
			bd.pstmt.setLong(i++, f.getLimite());
		} else {
			bd.pstmt.setLong(i++, Long.MAX_VALUE);
		}

		if (f.getOffset() != null) {
			bd.pstmt.setLong(i++, f.getOffset());
		} else {
			bd.pstmt.setLong(i++, 0);
		}

		System.out.println(bd.pstmt.toString());

		bd.rs = bd.pstmt.executeQuery();

		ArrayList<TarefaDTO> tarefas = new ArrayList<>();

		while (bd.rs.next()) {
			TarefaDTO t = new TarefaDTO();

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

	public long atualizar(BaseDTO dto) throws SQLException {
		String sql =
			"UPDATE tarefas SET\n" +
			"	titulo = ?,\n" +
			"	descricao = ?,\n" +
			"	cor = ?,\n" +
			"	prioridade = ?,\n" +
			"	data_criacao = ?,\n" +
			"	data_concluida = ?,\n" +
			"	data_limite = ?\n" +
			"WHERE\n" +
			"	TRUE\n" +
			"	AND id = ?;";

		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql);
		TarefaDTO tarefa = (TarefaDTO) dto;

		int i = 1;
		bd.pstmt.setString(i++, tarefa.getTitulo());
		bd.pstmt.setString(i++, tarefa.getDescricao());
		bd.pstmt.setString(i++, tarefa.getCor());
		bd.pstmt.setInt(i++, tarefa.getPrioridade());
		bd.pstmt.setTimestamp(i++, tarefa.getDataCriacao());
		bd.pstmt.setTimestamp(i++, tarefa.getDataConcluida());
		bd.pstmt.setTimestamp(i++, tarefa.getDataLimite());

		bd.pstmt.setLong(i, tarefa.getId());

		//System.out.println(bd.pstmt.toString());

		int atualizados = bd.pstmt.executeUpdate();
		bd.fecharConexao();

		//System.out.println(soma);

		if (atualizados > 0) {
			return tarefa.getId();
		}

		return 0;
	}

	public long excluir(BaseDTO filtros) throws SQLException {
		String sql = "DELETE FROM tarefas WHERE id = ?";
		int i = 1;

		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql);
		TarefaDTO f = (TarefaDTO) filtros;

		bd.pstmt.setLong(i++, f.getId());
		if (bd.pstmt.execute()) {
			bd.fecharConexao();
			return f.getId();
		} else {
			bd.fecharConexao();
			return 0;
		}
	}

}
