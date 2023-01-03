package todoso.backend.dados;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author mestre
 */
public class BdAcesso implements Closeable {
	// TODO: a URL poderia ficar num arquivo de configuração
	private final String URL = "jdbc:sqlite:todoso.db";

	public Connection conexao = null;
	public Statement stmt = null;
	public PreparedStatement pstmt = null;
	public ResultSet rs = null;

	private BdAcesso() throws SQLException {
		this.conexao = DriverManager.getConnection(URL);
		this.stmt = conexao.createStatement();
	}

	protected static BdAcesso abrirConexao() throws SQLException {
		return new BdAcesso();
	}

	protected void fecharConexao() {
		try {
			if (conexao == null || conexao.isClosed()) {
				return;
			}

			conexao.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected boolean existeTabela(String nomeTabela) throws SQLException {
		DatabaseMetaData meta = conexao.getMetaData();
		ResultSet tabelas = meta.getTables(null, null, nomeTabela, null);
		if (tabelas.next()) {
			return true;
		}
		return false;
	}

	@Override
	public void close() {
		fecharConexao();
	}
}
