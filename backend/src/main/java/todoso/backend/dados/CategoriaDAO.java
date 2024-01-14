package todoso.backend.dados;

import java.util.ArrayList;
import java.util.HashMap;

import java.sql.SQLException;

public class CategoriaDAO implements BaseDAO {

	private BdAcesso bd = null;

	public CategoriaDAO(BdAcesso bd) throws SQLException {
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
			"INSERT INTO categorias (\n" +
			"	nome_categoria\n" +
			")\n" +
			"VALUES (?);";

		bd.pstmt = bd.conexao.prepareStatement(sql, bd.RETURN_GENERATED_KEYS);

		CategoriaDTO c = (CategoriaDTO) dto;
		int i = 1;
		long idGerado = 0;
		bd.pstmt.setString(i++, c.getNome());
		bd.pstmt.execute();

		idGerado = bd.getChaveGerada();

		bd.pstmt.close();

		return idGerado;
	}

	public ArrayList<CategoriaDTO> selecionar(BaseDTO filtros) throws SQLException {
		String sql =
			"SELECT * FROM categorias c WHERE\n" +
			"	TRUE\n" +
			"	AND (id LIKE ? OR id IS NULL)\n" +
			"	AND (nome_categoria LIKE ? OR nome_categoria IS NULL)\n" +
			"ORDER BY c.id\n" +
			"LIMIT ? OFFSET ?;";

		bd.pstmt = bd.conexao.prepareStatement(sql);

		int i = 1;
		CategoriaDTO c = (CategoriaDTO) filtros;
		bd.pstmt.setString(i++, c.filtroLike(c.getId()));
		bd.pstmt.setString(i++, c.filtroLike(c.getNome()));
		bd.pstmt.setLong(i++, c.getLimite());
		bd.pstmt.setLong(i++, c.getOffset());

		//System.out.println(bd.pstmt.toString());

		bd.rs = bd.pstmt.executeQuery();

		ArrayList<CategoriaDTO> categorias = new ArrayList<>();

		CategoriaDTO ca;
		while (bd.rs.next()) {
			ca = new CategoriaDTO();
			ca.setId(bd.rs.getLong("id"));
			ca.setNome(bd.rs.getString("nome_categoria"));

			categorias.add(ca);
		}

		bd.pstmt.close();
		return categorias;
	}

	public long atualizar(BaseDTO dto) throws SQLException {
		String sql =
			"UPDATE categorias SET\n" +
			"	nome_categoria = ?\n" +
			"WHERE\n" +
			"	TRUE\n" +
			"	AND id = ?;";

		bd.pstmt = bd.conexao.prepareStatement(sql, bd.RETURN_GENERATED_KEYS);

		CategoriaDTO c = (CategoriaDTO) dto;
		int i = 1;

		bd.pstmt.setString(i++, c.getNome());
		bd.pstmt.setLong(i++, c.getId());
		bd.pstmt.close();

		if (bd.pstmt.executeUpdate() > 0) {
			return c.getId();
		}

		return -1;
	}

	public long excluir(BaseDTO filtros) throws SQLException {
		String sql = "DELETE FROM categorias WHERE id = ?;";
		CategoriaDTO c = (CategoriaDTO) filtros;
		int i = 1;

		bd.pstmt = bd.conexao.prepareStatement(sql);

		bd.pstmt.setLong(i++, c.getId());

		int deletadas = bd.pstmt.executeUpdate();
		bd.pstmt.close();

		return deletadas;
	}

	/**
	 * <p>Usa os IDs informados nos DTOs da tarefa e da categoria para relacioná-las.
	 * É importante que elas já existam no banco de dados.</p>
	 *
	 * @param tarefa
	 * @param categoria
	 * @return 'true' se a relação foi criada ou já existe; 'false' em caso contrário.
	 */
	public boolean relacionarTarefaCategoria(TarefaDTO tarefa, CategoriaDTO categoria) throws SQLException {
		// Verifica a existência da categoria
		String sql =
			"SELECT * FROM categorias\n" +
			"WHERE id = ?;";

		bd.pstmt = bd.conexao.prepareStatement(sql);
		bd.pstmt.setLong(1, categoria.getId());
		bd.rs = bd.pstmt.executeQuery();

		if (!bd.rs.next()) {
			bd.pstmt.close();
			return false;
		}

		// Verifica se a relação já existe
		sql =
			"SELECT id_tarefa, id_categoria\n" +
			"FROM tarefas_categorias\n" +
			"WHERE id_tarefa = ? AND id_categoria = ?;";

		bd.pstmt = bd.conexao.prepareStatement(sql);
		bd.pstmt.setLong(1, tarefa.getId());
		bd.pstmt.setLong(2, categoria.getId());
		bd.rs = bd.pstmt.executeQuery();

		if (bd.rs.next()) {
			// Relação já existe
			bd.pstmt.close();
			return true;
		}

		// Cria a relação tarefa-categoria.
		sql =
			"INSERT INTO tarefas_categorias(\n" +
			"	id_tarefa,\n" +
			"	id_categoria\n" +
			")\n" +
			"VALUES(?, ?);";


		bd.pstmt = bd.conexao.prepareStatement(sql);
		bd.pstmt.setLong(1, tarefa.getId());
		bd.pstmt.setLong(2, categoria.getId());
		bd.pstmt.execute();
		bd.pstmt.close();

		return true;
	}

	/**
	 * <p> Usa os IDs informados nos DTOs da tarefa e da categoria para desfazer
	 * o relacionamento entre elas no banco de dados.</p>
	 * <p>Se for informada ou uma categoria ou uma tarefa, então todas as suas
	 * relações são desfeitas.</p>
	 * <p>As tarefas não devem ficar sem categoria, portanto você deve usar o
	 * método relacionarCategoriaPadrao() em seguida.</p>
	 *
	 * @param tarefa
	 * @param categoria
	 */
	public int desfazerRelacaoTarefaCategoria(TarefaDTO tarefa, CategoriaDTO categoria)
		throws SQLException {
		
		String sql;

		if (tarefa == null && categoria == null) {
			throw new IllegalArgumentException("At least one argument should not be null.");
		}

		if (categoria == null) {
			sql =
				"DELETE FROM tarefas_categorias\n" +
				"WHERE id_tarefa = ?;";
		}
		else if (tarefa == null) {
			sql =
				"DELETE FROM tarefas_categorias\n" +
				"WHERE id_categoria = ?;";
		}
		else {
			sql =
				"DELETE FROM tarefas_categorias\n" +
				"WHERE id_tarefa = ? AND id_categoria = ?;";
		}

		int i = 1;
		bd.pstmt = bd.conexao.prepareStatement(sql);
		if (tarefa != null) {
			bd.pstmt.setLong(i++, tarefa.getId());
		}
		if (categoria != null) {
			bd.pstmt.setLong(i++, categoria.getId());
		}
		int alteracoes = bd.pstmt.executeUpdate();
		bd.pstmt.close();

		return alteracoes;
	}

	public void relacionarCategoriaPadrao() throws SQLException {
		String sql =
			"INSERT INTO tarefas_categorias (id_tarefa, id_categoria)\n" +
			"SELECT t.id, 1 AS categoria_padrao FROM tarefas_categorias tc\n" +
			"RIGHT JOIN tarefas t ON tc.id_tarefa = t.id\n" +
			"WHERE tc.id_tarefa IS NULL;";

		bd.pstmt = bd.conexao.prepareStatement(sql);
		bd.pstmt.execute();
	}

	/**
	 *
	 * @param tarefas
	 * @return
	 * @throws SQLException
	 */
	public HashMap<Long, ArrayList<CategoriaDTO>> selecionarCategoriasPorTarefa(
		ArrayList<TarefaDTO> tarefas) throws SQLException {

		HashMap<Long, ArrayList<CategoriaDTO>> retorno = new HashMap<>();

		if (tarefas.size() <= 0) {
			return retorno;
		}

		String sqlEstatico =
			"SELECT * FROM categorias c\n" +
			"INNER JOIN tarefas_categorias tc ON c.id = tc.id_categoria\n" +
			"WHERE tc.id_tarefa IN (";
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
					new ArrayList<CategoriaDTO>()
				);
			}

			retorno.get(bd.rs.getLong("id_tarefa")).add(
				new CategoriaDTO(
					bd.rs.getLong("id_categoria"),
					bd.rs.getString("nome_categoria")
				)
			);
		}

		return retorno;
	}

}
