package todoso.backend.dados;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author savio
 */
public abstract class Olar {
	
	public static void criarTabelas() throws SQLException {
		String sqlCategorias =
			"CREATE TABLE IF NOT EXISTS categorias (\n" +
			"	id integer NOT NULL,\n" +
			"	nome_categoria varchar(48) NOT NULL,\n" +
			"	CONSTRAINT categorias_pk PRIMARY KEY (id)\n" +
			")\n";
		
		String sqlTags =
			"CREATE TABLE IF NOT EXISTS tags (\n" +
			"	id integer NOT NULL,\n" +
			"	nome_tag varchar(48) NOT NULL,\n" +
			"	CONSTRAINT tags_pk PRIMARY KEY (id)\n" +
			")\n";
		
		String sqlArquivos =
			"CREATE TABLE IF NOT EXISTS arquivos (\n" +
			"	id integer NOT NULL,\n" +
			"	url varchar NOT NULL,\n" +
			"	mime varchar NOT NULL,\n" +
			"	tamanho integer NULL,\n" +
			"	CONSTRAINT arquivos_pk PRIMARY KEY (id)\n" +
			")\n";
		
		String sqlUsuarios =
			"CREATE TABLE IF NOT EXISTS usuarios (\n" +
			"	id integer NOT NULL,\n" +
			"	nome_usuario varchar(128) NOT NULL,\n" +
			"	email varchar(128) NOT NULL,\n" +
			"	senha_hash varchar NOT NULL,\n" +
			"	data_ultimo_login timestamp NULL,\n" +
			"	id_foto_arquivo integer,\n" +
			"	deletado boolean NULL,\n" +
			"	CONSTRAINT usuarios_pk PRIMARY KEY (id),\n" +
			"	CONSTRAINT usuarios_fk FOREIGN KEY (id_foto_arquivo) "
				+ "REFERENCES arquivos(id)\n" +
			")\n";
		
		String sqlTokens =
			"CREATE TABLE IF NOT EXISTS tokens (\n" +
			"	id integer NOT NULL,\n" +
			"	token varchar NOT NULL,\n" +
			"	data_criacao timestamp NOT NULL,\n" +
			"	id_usuario integer NOT NULL,\n" +
			"	CONSTRAINT tokens_pk PRIMARY KEY (id),\n" +
			"	CONSTRAINT tokens_fk FOREIGN KEY (id_usuario) "
				+ "REFERENCES usuarios(id)\n" +
			")\n";
		
		String sqlTarefas =
			"CREATE TABLE IF NOT EXISTS tarefas (\n" +
			"	id integer NOT NULL,\n" +
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
			"	id_tarefa integer NOT NULL,\n" +
			"	id_tag integer NOT NULL,\n" +
			"	CONSTRAINT tarefas_tags_fk FOREIGN KEY (id_tarefa) "
				+ "REFERENCES tarefas(id),\n" +
			"	CONSTRAINT tarefas_tags_fk_1 FOREIGN KEY (id_tag) "
				+ "REFERENCES tags(id)\n" +
			")\n";
		
		String sqlTarefas_categorias =
			"CREATE TABLE IF NOT EXISTS tarefas_categorias (\n" +
			"	id_tarefa integer NOT NULL,\n" +
			"	id_categoria integer NOT NULL,\n" +
			"	CONSTRAINT tarefas_categorias_fk FOREIGN KEY (id_tarefa) "
				+ "REFERENCES tarefas(id),\n" +
			"	CONSTRAINT tarefas_categorias_fk_1 FOREIGN KEY (id_categoria) "
				+ "REFERENCES categorias(id)\n" +
			")\n";
		
		String sqlTarefas_usuarios =
			"CREATE TABLE IF NOT EXISTS tarefas_usuarios (\n" +
			"	id_tarefa integer NOT NULL,\n" +
			"	id_usuario integer NOT NULL,\n" +
			"	permissoes bit(16) NULL,\n" +
			"	CONSTRAINT tarefas_usuarios_fk FOREIGN KEY (id_tarefa) "
				+ "REFERENCES tarefas(id),\n" +
			"	CONSTRAINT tarefas_usuarios_fk_1 FOREIGN KEY (id_usuario) "
				+ "REFERENCES usuarios(id)\n" +
			")\n";

		String sqlTarefas_arquivos =
			"CREATE TABLE IF NOT EXISTS tarefas_usuarios (\n" +
			"	id_tarefa integer NOT NULL,\n" +
			"	id_usuario integer NOT NULL,\n" +
			"	permissoes bit(16) NULL,\n" +
			"	CONSTRAINT tarefas_usuarios_fk FOREIGN KEY (id_tarefa) "
				+ "REFERENCES tarefas(id),\n" +
			"	CONSTRAINT tarefas_usuarios_fk_1 FOREIGN KEY (id_usuario) "
				+ "REFERENCES usuarios(id)\n" +
			")\n";
		
		BdAcesso bd = BdAcesso.abrirConexao();

		bd.statement.addBatch(sqlCategorias);
		bd.statement.addBatch(sqlTags);
		bd.statement.addBatch(sqlArquivos);
		bd.statement.addBatch(sqlUsuarios);
		bd.statement.addBatch(sqlTokens);
		bd.statement.addBatch(sqlTarefas);
		bd.statement.addBatch(sqlTarefas_tags);
		bd.statement.addBatch(sqlTarefas_categorias);
		bd.statement.addBatch(sqlTarefas_usuarios);
		bd.statement.addBatch(sqlTarefas_arquivos);
		
		bd.statement.executeBatch();
		
		bd.fecharConexao();
	}
}
