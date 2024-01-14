package todoso.backend.dados;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TagsDAO implements BaseDAO {

	private BdAcesso bd = null;

	public TagsDAO(BdAcesso bd) throws SQLException {
		if (bd != null && bd.conexao != null && !bd.conexao.isClosed()) {
			this.bd = bd;
		}
		else {
			throw new SQLException("Não foi fornecida uma conexão válida " +
				"com o banco de dados.");
		}
	}

	@Override
	public long inserir(BaseDTO dto) throws SQLException {
		final String sql = "INSERT INTO tags (nome_tag) VALUES (?);";

		bd.pstmt = bd.conexao.prepareStatement(sql, bd.RETURN_GENERATED_KEYS);
		TagDTO tag = (TagDTO) dto;
		int i = 1;
		long idGerado = 0;
		bd.pstmt.setString(i++, tag.getNome());
		bd.pstmt.execute();

		idGerado = bd.getChaveGerada();

		bd.pstmt.close();

		return idGerado;
	}

	@Override
	public ArrayList<? extends BaseDTO> selecionar(BaseDTO filtros) throws SQLException {
		final String sql =
			"SELECT * FROM tags WHERE\n" +
			"	TRUE\n" +
			"	AND (id LIKE ? OR id IS NULL)\n" +
			"	AND (nome_tag LIKE ? OR nome_tag IS NULL)\n" +
			"ORDER BY id\n" +
			"LIMIT ? OFFSET ?;";

		bd.pstmt = bd.conexao.prepareStatement(sql);

		int i = 1;
		TagDTO c = (TagDTO) filtros;
		bd.pstmt.setString(i++, c.filtroLike(c.getId()));
		bd.pstmt.setString(i++, c.filtroLike(c.getNome()));
		bd.pstmt.setLong(i++, c.getLimite());
		bd.pstmt.setLong(i++, c.getOffset());

		bd.rs = bd.pstmt.executeQuery();

		ArrayList<TagDTO> tags = new ArrayList<>();
	
		TagDTO tag;
		while (bd.rs.next()) {
			tag = new TagDTO();
			tag.setId(bd.rs.getLong("id"));
			tag.setNome(bd.rs.getString("nome_tag"));

			tags.add(tag);
		}

		bd.pstmt.close();

		return tags;
	}

	/**
	 * 
	 * @param filtros
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<? extends BaseDTO> selecionarTagsPorTarefa(BaseDTO filtros) throws SQLException {
		final String sql = "";
		return null;
	}

	@Override
	public long atualizar(BaseDTO dto) throws SQLException {
		String sql =
			"UPDATE tags SET\n" +
			"	nome_tag = ?\n" +
			"WHERE\n" +
			"	TRUE\n" +
			"	AND id = ?;";

		bd.pstmt = bd.conexao.prepareStatement(sql, bd.RETURN_GENERATED_KEYS);

		TagDTO tag = (TagDTO) dto;
		int i = 1;

		bd.pstmt.setString(i++, tag.getNome());
		bd.pstmt.setLong(i++, tag.getId());
		bd.pstmt.close();

		if (bd.pstmt.executeUpdate() > 0) {
			return tag.getId();
		}

		return -1;
	}

	@Override
	public long excluir(BaseDTO filtros) throws SQLException {
		String sql = "DELETE FROM tags WHERE id = ?;";
		TagDTO c = (TagDTO) filtros;
		int i = 1;

		bd.pstmt = bd.conexao.prepareStatement(sql);

		bd.pstmt.setLong(i++, c.getId());

		int deletadas = bd.pstmt.executeUpdate();
		bd.pstmt.close();

		// TODO: indicar se foi ou não excluído
		return deletadas;
	}

	/**
	 * <p>Usa os IDs informados nos DTOs da tarefa e da tag para relacioná-las.
	 * É importante que ambas já existam no banco de dados.</p>
	 *
	 * @param tarefa
	 * @param tag
	 * @return 'true' se a relação foi criada ou já existe; 'false' em caso contrário.
	 */
	public boolean relacionarTarefaTag(TarefaDTO tarefa, TagDTO tag) throws SQLException {
		// Verifica a existência da tag
		String sql =
			"SELECT * FROM tags\n" +
			"WHERE id = ?;";

		bd.pstmt = bd.conexao.prepareStatement(sql);
		bd.pstmt.setLong(1, tag.getId());
		bd.rs = bd.pstmt.executeQuery();

		if (!bd.rs.next()) {
			// A tag não existe.
			bd.pstmt.close();
			return false;
		}

		// Verifica se a relação já existe
		sql =
			"SELECT id_tarefa, id_tag\n" +
			"FROM tarefas_tags\n" +
			"WHERE id_tarefa = ? AND id_tag = ?;";

		bd.pstmt = bd.conexao.prepareStatement(sql);
		bd.pstmt.setLong(1, tarefa.getId());
		bd.pstmt.setLong(2, tag.getId());
		bd.rs = bd.pstmt.executeQuery();

		if (bd.rs.next()) {
			// Relação já existe
			bd.pstmt.close();
			return true;
		}

		// Cria a relação tarefa-tag.
		sql =
			"INSERT INTO tarefas_tags(\n" +
			"	id_tarefa,\n" +
			"	id_tag\n" +
			")\n" +
			"VALUES(?, ?);";


		bd.pstmt = bd.conexao.prepareStatement(sql);
		bd.pstmt.setLong(1, tarefa.getId());
		bd.pstmt.setLong(2, tag.getId());
		int alteracoes = bd.pstmt.executeUpdate();
		bd.pstmt.close();

		return (alteracoes >= 1);
	}

	/**
	 * <p> Usa os IDs informados nos DTOs da tarefa e da tag para desfazer
	 * o relacionamento entre elas no banco de dados.</p>
	 * <p>Se for informada ou uma tag ou uma tarefa, então todas as suas
	 * relações são desfeitas.</p>
	 *
	 * @param tarefa
	 * @param tag
	 */
	public int desfazerRelacaoTarefaTag(TarefaDTO tarefa, TagDTO tag) throws SQLException {
		String sql;

		if (tarefa == null && tag == null) {
			throw new IllegalArgumentException("At least one argument should not be null.");
		}

		if (tag == null) {
			sql =
				"DELETE FROM tarefas_tags\n" +
				"WHERE id_tarefa = ?;";
		}
		else if (tarefa == null) {
			sql =
				"DELETE FROM tarefas_tags\n" +
				"WHERE id_tag = ?;";
		}
		else {
			sql =
				"DELETE FROM tarefas_tags\n" +
				"WHERE id_tarefa = ? AND id_tag = ?;";
		}

		int i = 1;
		bd.pstmt = bd.conexao.prepareStatement(sql);
		if (tarefa != null) {
			bd.pstmt.setLong(i++, tarefa.getId());
		}
		if (tag != null) {
			bd.pstmt.setLong(i++, tag.getId());
		}
		int alteracoes = bd.pstmt.executeUpdate();
		bd.pstmt.close();

		return alteracoes;
	}

	/**
	 *
	 * @param tarefas
	 * @return
	 * @throws SQLException
	 */
	public HashMap<Long, ArrayList<TagDTO>> selecionarTagsPorTarefa(
		ArrayList<TarefaDTO> tarefas) throws SQLException {

		HashMap<Long, ArrayList<TagDTO>> retorno = new HashMap<>();

		if (tarefas.size() <= 0) {
			return retorno;
		}

		final String sqlEstatico =
			"SELECT * FROM tags t\n" +
			"INNER JOIN tarefas_tags tt ON t.id = tt.id_tag\n" +
			"WHERE tt.id_tarefa IN (";
		StringBuffer sql = new StringBuffer(sqlEstatico);

		for (int i = 0; i < tarefas.size(); i++) {
			TarefaDTO d = tarefas.get(i);
			sql.append(d.getId());

			if (i+1 < tarefas.size()) sql.append(",");
			else sql.append(");");
		}

		bd.pstmt = bd.conexao.prepareStatement(sql.toString());
		//System.out.println(bd.pstmt);
		bd.rs = bd.pstmt.executeQuery();

		while (bd.rs.next()) {
			if (retorno.get(bd.rs.getLong("id_tarefa")) == null) {
				retorno.put(
					bd.rs.getLong("id_tarefa"),
					new ArrayList<TagDTO>()
				);
			}

			retorno.get(bd.rs.getLong("id_tarefa")).add(
				new TagDTO(
					bd.rs.getLong("id_tag"),
					bd.rs.getString("nome_tag")
				)
			);
		}

		return retorno;
	}

	/**
	 * Tags não possuem existência própria, por isso é necessário excluir as tags
	 * sem tarefas associadas.
	 * 
	 * @return Quantas tags foram removidas.
	 */
	public int excluirTagsSemTarefas() throws SQLException {
		final String sql =
			"DELETE FROM tags WHERE id IN (\n" +
			"\tSELECT t.id FROM tags t\n" +
			"\tLEFT JOIN tarefas_tags tt ON t.id = tt.id_tag\n" +
			"\tWHERE tt.id_tag IS NULL\n" +
			");";
		
		bd.pstmt = bd.conexao.prepareStatement(sql);
		int exclusoes = bd.pstmt.executeUpdate();

		bd.pstmt.close();

		return exclusoes;
	}
}