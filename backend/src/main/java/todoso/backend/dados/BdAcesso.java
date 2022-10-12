package todoso.backend.dados;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
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
	public Statement statement = null;
	public ResultSet resultSet = null;
	
	private BdAcesso() throws SQLException {
		this.conexao = DriverManager.getConnection(URL);
		this.statement = conexao.createStatement();
	}
	
	protected static BdAcesso abrirConexao() throws SQLException {
		BdAcesso db = new BdAcesso();
		return db;
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

	@Override
	public void close() {
		fecharConexao();
	}
}
