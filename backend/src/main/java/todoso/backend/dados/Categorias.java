package todoso.backend.dados;

import java.util.ArrayList;
import java.sql.SQLException;

public class Categorias implements Dados {
	
	public Categorias() {}

	public long inserir(BaseDTO dto) throws SQLException {
		String sql =
			"INSERT INTO categorias (\n" +
			"	nome_categoria\n" +
			")\n" +
			"VALUES (?);";

		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql, bd.RETURN_GENERATED_KEYS);

		CategoriaDTO c = (CategoriaDTO) dto;
		int i = 1;
		long idGerado = 0;
		bd.pstmt.setString(i++, c.getNome());
		bd.pstmt.execute();

		idGerado = bd.getChaveGerada();

		bd.fecharConexao();

		return idGerado;
	}

	public ArrayList<CategoriaDTO> selecionar(BaseDTO filtros) throws SQLException {
		String sql =
			"SELECT * FROM categorias c WHERE\n" +
			"	TRUE\n" +
			"	AND id LIKE ?\n" +
			"	AND nome_categoria LIKE ?\n" +
			"ORDER BY c.id\n" +
			"LIMIT ? OFFSET ?;";

		BdAcesso bd = BdAcesso.abrirConexao();
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

			System.out.println(ca.getId());
			System.out.println(ca.getNome());

			categorias.add(ca);
		}

		return categorias;
	}

	public long atualizar(BaseDTO dto) throws SQLException {
		String sql =
			"UPDATE categorias SET\n" +
			"	nome_categoria = ?\n" +
			"WHERE\n" +
			"	TRUE\n" +
			"	AND id = ?;";

		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql, bd.RETURN_GENERATED_KEYS);

		CategoriaDTO c = (CategoriaDTO) dto;
		int i = 1;

		bd.pstmt.setString(i++, c.getNome());
		bd.pstmt.setLong(i++, c.getId());

		if (bd.pstmt.executeUpdate() > 0) {
			return c.getId();
		}
		bd.fecharConexao();

		return -1;
	}

	public long excluir(BaseDTO filtros) throws SQLException {
		String sql = "DELETE FROM categorias WHERE id = ?;";
		CategoriaDTO c = (CategoriaDTO) filtros;
		int i = 1;

		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql);

		bd.pstmt.setLong(i++, c.getId());

		if (bd.pstmt.execute()) {
			bd.fecharConexao();
			return c.getId();
		} else {
			bd.fecharConexao();
			return -1;
		}
	}

}
