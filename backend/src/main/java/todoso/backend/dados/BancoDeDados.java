package todoso.backend.dados;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author mestre
 */
public class BancoDeDados {
	private final String URL = "jdbc:sqlite:todoso.db";
	private Connection conn = null;
	
	private BancoDeDados() throws SQLException {
		this.conn = abrirConexao();
		
		if (conn == null) {
			throw new SQLException("Sem conexão!");
		}
		
		criarTabelas(conn);
	}
	
	private Connection abrirConexao() {
		try {
			if (conn != null && !conn.isClosed()) {
				return conn;
			}
			return DriverManager.getConnection(URL);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void criarTabelas(Connection conn) {
		
		String sqlCategorias =
			"CREATE TABLE IF NOT EXISTS categorias (\n" +
			"	id serial NOT NULL,\n" +
			"	nome_categoria varchar(48) NOT NULL,\n" +
			"	CONSTRAINT categorias_pk PRIMARY KEY (id)\n" +
			")\n";
		
		String sqlTags =
			"CREATE TABLE IF NOT EXISTS tags (\n" +
			"	id serial NOT NULL,\n" +
			"	nome_tag varchar(48) NOT NULL,\n" +
			"	CONSTRAINT tags_pk PRIMARY KEY (id)\n" +
			")\n";
		
		String sqlArquivos =
			"CREATE TABLE IF NOT EXISTS arquivos (\n" +
			"	id serial NOT NULL,\n" +
			"	url varchar NOT NULL,\n" +
			"	mime varchar NOT NULL,\n" +
			"	tamanho integer NULL,\n" +
			"	CONSTRAINT arquivos_pk PRIMARY KEY (id)\n" +
			")\n";
		
		String sqlUsuarios =
			"CREATE TABLE IF NOT EXISTS usuarios (\n" +
			"	id serial NOT NULL,\n" +
			"	nome_usuario varchar(128) NOT NULL,\n" +
			"	email varchar(128) NOT NULL,\n" +
			"	senha_hash varchar NOT NULL,\n" +
			"	data_ultimo_login timestamp NULL,\n" +
			"	id_foto_arquivo serial,\n" +
			"	deletado boolean NULL,\n" +
			"	CONSTRAINT usuarios_pk PRIMARY KEY (id),\n" +
			"	CONSTRAINT usuarios_fk FOREIGN KEY (id_foto_arquivo) "
				+ "REFERENCES arquivos(id)\n" +
			")\n";
		
		String sqlTokens =
			"CREATE TABLE IF NOT EXISTS tokens (\n" +
			"	id serial NOT NULL,\n" +
			"	token varchar NOT NULL,\n" +
			"	data_criacao timestamp NOT NULL,\n" +
			"	id_usuario serial NOT NULL,\n" +
			"	CONSTRAINT tokens_pk PRIMARY KEY (id),\n" +
			"	CONSTRAINT tokens_fk FOREIGN KEY (id_usuario) "
				+ "REFERENCES usuarios(id)\n" +
			")\n";
		
		String sqlTarefas =
			"CREATE TABLE IF NOT EXISTS tarefas (\n" +
			"	id serial NOT NULL,\n" +
			"	titulo varchar(128) NOT NULL,\n" +
			"	descricao varchar(8192) NULL,\n" +
			"	cor char(7) NULL,\n" +
			"	prioridade integer NULL,\n" +
			"	data_criacao timestamp NOT NULL,\n" +
			"	data_concluida timestamp NULL,\n" +
			"	data_limite timestamp NULL,\n" +
			"	CONSTRAINT tarefas_pk PRIMARY KEY (id)\n" +
			")\n";
		
		String sqlTarefas_tags =
			"CREATE TABLE IF NOT EXISTS tarefas_tags (\n" +
			"	id_tarefa serial NOT NULL,\n" +
			"	id_tag serial NOT NULL,\n" +
			"	CONSTRAINT tarefas_tags_fk FOREIGN KEY (id_tarefa) "
				+ "REFERENCES tarefas(id),\n" +
			"	CONSTRAINT tarefas_tags_fk_1 FOREIGN KEY (id_tag) "
				+ "REFERENCES tags(id)\n" +
			")\n";
		
		String sqlTarefas_categorias =
			"CREATE TABLE IF NOT EXISTS tarefas_categorias (\n" +
			"	id_tarefa serial NOT NULL,\n" +
			"	id_categoria serial NOT NULL,\n" +
			"	CONSTRAINT tarefas_categorias_fk FOREIGN KEY (id_tarefa) "
				+ "REFERENCES tarefas(id),\n" +
			"	CONSTRAINT tarefas_categorias_fk_1 FOREIGN KEY (id_categoria) "
				+ "REFERENCES categorias(id)\n" +
			")\n";
		
		String sqlTarefas_usuarios =
			"CREATE TABLE IF NOT EXISTS tarefas_usuarios (\n" +
			"	id_tarefa serial NOT NULL,\n" +
			"	id_usuario serial NOT NULL,\n" +
			"	permissoes bit(16) NULL,\n" +
			"	CONSTRAINT tarefas_usuarios_fk FOREIGN KEY (id_tarefa) "
				+ "REFERENCES tarefas(id),\n" +
			"	CONSTRAINT tarefas_usuarios_fk_1 FOREIGN KEY (id_usuario) "
				+ "REFERENCES usuarios(id)\n" +
			")\n";

		String sqlTarefas_arquivos =
			"CREATE TABLE IF NOT EXISTS tarefas_usuarios (\n" +
			"	id_tarefa serial NOT NULL,\n" +
			"	id_usuario serial NOT NULL,\n" +
			"	permissoes bit(16) NULL,\n" +
			"	CONSTRAINT tarefas_usuarios_fk FOREIGN KEY (id_tarefa) "
				+ "REFERENCES tarefas(id),\n" +
			"	CONSTRAINT tarefas_usuarios_fk_1 FOREIGN KEY (id_usuario) "
				+ "REFERENCES usuarios(id)\n" +
			")\n";
		
		try {
			Statement stmt = conn.createStatement();
	
			stmt.addBatch(sqlCategorias);
			stmt.addBatch(sqlTags);
			stmt.addBatch(sqlArquivos);
			stmt.addBatch(sqlUsuarios);
			stmt.addBatch(sqlTokens);
			stmt.addBatch(sqlTarefas);
			stmt.addBatch(sqlTarefas_tags);
			stmt.addBatch(sqlTarefas_categorias);
			stmt.addBatch(sqlTarefas_usuarios);
			stmt.addBatch(sqlTarefas_arquivos);
			
			stmt.executeBatch();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static BancoDeDados abrir() {
		try {
			BancoDeDados db = new BancoDeDados();
			return db;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void fechar() {
		try {
			if (conn == null || conn.isClosed()) {
				return;
			}
			
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
