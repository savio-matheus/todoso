package todoso.arquivo.dados;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Sávio
 */
public class ArquivoDAO {
	// TODO: a URL deveria ficar num arquivo de configuração
	private final String URL = "jdbc:sqlite:../todoso.db";

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public ArquivoDAO() throws SQLException {
		this.conn = DriverManager.getConnection(URL);
		this.stmt = conn.createStatement();
	}
	
	public String inserir(MultipartFile arquivo, long idTarefa) throws SQLException, IOException {
		String sql =
			"INSERT INTO arquivos (url, mime, tamanho) VALUES\n" +
			"(?, ?, ?)";
		
		int i = 1;
		String nomeArquivo = idTarefa + "_" + arquivo.getOriginalFilename();

		pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		pstmt.setString(i++, nomeArquivo);
		pstmt.setString(i++, arquivo.getContentType());
		pstmt.setLong(i++, arquivo.getSize());
		pstmt.execute();
		
		rs = pstmt.getGeneratedKeys();
		
		long idArquivo = -1;
		if (rs.next()) {
			idArquivo = rs.getLong(1);
		}
		
		if (idArquivo == -1) {
			throw new SQLException("File could not be stored.");
		}
		
		sql =
			"INSERT INTO tarefas_arquivos (id_tarefa, id_arquivo) VALUES\n" +
			"(?, ?);";
		pstmt = conn.prepareStatement(sql);
		i = 1;
		pstmt.setLong(i++, idTarefa);
		pstmt.setLong(i++, idArquivo);
		pstmt.execute();
		
		return nomeArquivo;
	}
	
	public boolean deletar(long idArquivo) throws SQLException {
		String sql =
			"DELETE FROM arquivos WHERE id = ?;";
		pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, idArquivo);
		pstmt.execute();
		
		sql =
			"DELETE FROM tarefas_arquivos WHERE id_arquivo = ?;";
		pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, idArquivo);
		pstmt.execute();
		
		return true;
	}
	
	public long buscarIdPorNome(String nomeArquivo) throws SQLException {
		String sql = "SELECT * FROM arquivos WHERE url = ?;";
		pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, nomeArquivo);
		rs = pstmt.executeQuery();
		
		long idArquivo = -1;
		
		while (rs.next()) {
			idArquivo = rs.getLong("id");
		}

		return idArquivo;
	}
}
