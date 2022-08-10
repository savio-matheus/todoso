package backend.persistencia;

import java.sql.*;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.postgresql.Driver;

/**
 *
 * @author mestre
 */
class Persistencia {

	public static Connection abreConexaoBD() throws SQLException, NamingException {
		InitialContext ctx = new InitialContext();
		if (ctx == null) {
			throw new NamingException();
		}
		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/postgres");
		if (ds == null) {
			throw new SQLException();
		}
		return ds.getConnection();
	}
	
	//TODO:
	public static String sanitizaString(String str) {
		return str;
	}
}
