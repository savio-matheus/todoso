package todoso.backend.dados;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author savio
 */

public class TarefasDAO implements BaseDAO {

	private BdAcesso bd = null;

	public TarefasDAO(BdAcesso bd) throws SQLException {
		if (bd != null && bd.conexao != null && !bd.conexao.isClosed()) {
			this.bd = bd;
		}
		else {
			throw new SQLException("Não foi fornecida uma conexão válida " +
				"com o banco de dados.");
		}
	}

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
		bd.pstmt.close();

		return idGerado;
	}

	public ArrayList<TarefaDTO> selecionar(BaseDTO filtros) throws SQLException {
		String sql =
			"SELECT t.* FROM tarefas t \n" +
			"INNER JOIN tarefas_categorias tc ON tc.id_tarefa = t.id\n" +
			"INNER JOIN categorias c ON c.id = tc.id_categoria\n" +
			"	WHERE TRUE\n" +
			"	AND (t.id LIKE ? OR t.id IS NULL)\n" +
			"	AND (t.titulo LIKE ? OR t.titulo IS NULL)\n" +
			"	AND (t.descricao LIKE ? OR t.descricao IS NULL)\n" +
			"	AND (t.cor LIKE ? OR t.cor IS NULL)\n" +
			"	AND (t.prioridade LIKE ? OR t.prioridade IS NULL)\n" +
			"	AND (t.data_criacao LIKE ? OR t.data_criacao IS NULL)\n" +
			"	AND (t.data_concluida LIKE ? OR t.data_concluida IS NULL)\n" +
			"	AND (t.data_limite LIKE ? OR t.data_limite IS NULL)\n" +
			"	AND (tc.id_categoria LIKE ? OR tc.id_categoria IS NULL)\n" +
			"GROUP BY t.id\n" +
			"ORDER BY t.id\n" +
			"LIMIT ? OFFSET ?\n";

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

		if (f.getIdRelacionadoCategoria() != null) {
			bd.pstmt.setLong(i++, f.getIdRelacionadoCategoria());
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

		//System.out.println(bd.pstmt.toString());

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

		bd.pstmt.close();
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

		//System.out.println(soma);

		if (atualizados > 0) {
			return tarefa.getId();
		}

		bd.pstmt.close();
		return 0;
	}

	public long excluir(BaseDTO filtros) throws SQLException {
		String sql = "DELETE FROM tarefas WHERE id = ?";
		int i = 1;

		bd.pstmt = bd.conexao.prepareStatement(sql);
		TarefaDTO f = (TarefaDTO) filtros;

		bd.pstmt.setLong(i++, f.getId());
		bd.pstmt.execute();

		// TODO: utilizar as funções no DAO da categoria

		bd.pstmt.close();
		return f.getId();
	}

	public ArrayList<String> anexosPorTarefa(TarefaDTO tarefa) throws SQLException {
		// TODO: utilizar a mesma abordagem das categorias para diminuir a qtde
		// de consultas ao banco.
		String sql =
			"SELECT t.id, a.url FROM arquivos a\n" +
			"INNER JOIN tarefas_arquivos ta ON ta.id_arquivo = a.id\n" +
			"INNER JOIN tarefas t ON t.id = ta.id_tarefa\n" +
			"WHERE t.id LIKE ?;";

		bd.pstmt = bd.conexao.prepareStatement(sql);

		bd.pstmt.setLong(1, tarefa.getId());

		bd.rs = bd.pstmt.executeQuery();

		ArrayList<String> arquivos = new ArrayList<>();
		while (bd.rs.next()) {
			arquivos.add(bd.rs.getString("url"));
		}

		bd.pstmt.close();
		return arquivos;
	}

	/**
	 * Retorna uma lista tarefas (ids apenas) associadas com a tag informada
	 * nos filtros.
	 *
	 * @param filtros Com o id da tag preenchido.
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<? extends BaseDTO> selecionarTarefasPorTag(BaseDTO filtros)
		throws SQLException {

		final String sql =
			"SELECT t.id FROM tarefas t\n" +
			"INNER JOIN tarefas_tags tt ON t.id = tt.id_tarefa\n" +
			"INNER JOIN tags tg ON tg.id = tt.id_tag\n" +
			"WHERE tg.id = ?;";

		TagDTO filtrosTag = (TagDTO) filtros;
		if (filtrosTag.getId() == null) {
			throw new SQLException("É necessário informar o id da tag.");
		}

		bd.pstmt = bd.conexao.prepareStatement(sql);
		bd.pstmt.setObject(1, filtrosTag.getId());
		bd.rs = bd.pstmt.executeQuery();

		ArrayList<TagDTO> resultado = new ArrayList<>();
		while (bd.rs.next()) {
			TarefaDTO t = new TarefaDTO();
			t.setId(bd.rs.getLong("id"));
		}

		bd.rs.close();
		bd.pstmt.close();

		return resultado;
	}

	/**
	 * Retorna uma lista tarefas (ids apenas) associadas com a categoria informada
	 * nos filtros.
	 * 
	 * @param filtros Contendo o id da categoria.
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<? extends BaseDTO> selecionarTarefasPorCategoria(BaseDTO filtros)
		throws SQLException {

		final String sql =
			"SELECT t.id FROM tarefas t\n" +
			"INNER JOIN tarefas_categorias tc ON t.id = tc.id_tarefa\n" +
			"INNER JOIN categorias c ON c.id = tc.id_tarefa\n" +
			"WHERE tc.id_tarefa = ?;";

		CategoriaDTO filtrosCategoria = (CategoriaDTO) filtros;
		if (filtrosCategoria.getId() == null) {
			throw new SQLException("É necessário informar o id da categoria.");
		}

		bd.pstmt = bd.conexao.prepareStatement(sql);
		bd.pstmt.setObject(1, filtrosCategoria.getId());
		bd.rs = bd.pstmt.executeQuery();

		ArrayList<CategoriaDTO> resultado = new ArrayList<>();
		while (bd.rs.next()) {
			TarefaDTO t = new TarefaDTO();
			t.setId(bd.rs.getLong("id"));
		}

		bd.rs.close();
		bd.pstmt.close();

		return resultado;
	}
}
