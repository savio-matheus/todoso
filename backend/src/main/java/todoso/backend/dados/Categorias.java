package todoso.backend.dados;

import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;

public class Categorias implements Dados {
	
	public Categorias() {}

	public int inserir(BaseDTO dto) throws SQLException {
		ArrayList<CategoriaDTO> a = new ArrayList<>();
		a.add((CategoriaDTO )dto);
		return inserir(a);
	}

	public int inserir(ArrayList<? extends BaseDTO> dtos) throws SQLException {
		String sql =
			"INSERT INTO categorias (\n" +
			"	nome_categoria\n" +
			")\n" +
			"VALUES (?);";

		int alteracoes = 0;
		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql);

		for (BaseDTO b : dtos) {
			int i = 1;
			CategoriaDTO c = (CategoriaDTO) b;
			bd.pstmt.setString(i++, c.getNome());
			bd.pstmt.addBatch();
		}
		bd.pstmt.executeBatch();
		bd.fecharConexao();

		// TODO: trazer a quantidade correta de alterações (inserts)
		return alteracoes;
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

		System.out.println(bd.pstmt.toString());

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

	public int atualizar(BaseDTO dto) throws SQLException {
		ArrayList<CategoriaDTO> a = new ArrayList<>();
		a.add((CategoriaDTO )dto);
		return atualizar(a);
	}

	public int atualizar(ArrayList<? extends BaseDTO> dtos) throws SQLException {
		String sql =
			"UPDATE categorias SET\n" +
			"	nome_categoria = ?\n" +
			"WHERE\n" +
			"	TRUE\n" +
			"	AND id = ?;";

		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql);

		for (BaseDTO b : dtos) {
			CategoriaDTO c = (CategoriaDTO) b;
			int i = 1;

			bd.pstmt.setString(i++, c.getNome());
			bd.pstmt.setLong(i++, c.getId());

			System.out.println(c.getId());
			System.out.println(c.getNome());

			bd.pstmt.addBatch();
		}

		System.out.println(bd.pstmt.toString());

		int[] atualizados = bd.pstmt.executeBatch();
		bd.fecharConexao();

		int soma = 0;
		for (int i : atualizados) {
			soma += i;
		}

		return soma;
	}

	public int excluir(BaseDTO filtros) throws SQLException {
		String sql = "DELETE FROM categorias WHERE id = ?;";
		CategoriaDTO c = (CategoriaDTO) filtros;
		int i = 1;

		BdAcesso bd = BdAcesso.abrirConexao();
		bd.pstmt = bd.conexao.prepareStatement(sql);

		bd.pstmt.setLong(i++, c.getId());

		if (bd.pstmt.execute()) {
			bd.fecharConexao();
			return 1;
		} else {
			bd.fecharConexao();
			return 0;
		}
	}

}
