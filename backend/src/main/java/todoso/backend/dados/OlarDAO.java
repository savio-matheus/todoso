package todoso.backend.dados;

import java.sql.SQLException;

/**
 *
 * @author savio
 */
public class OlarDAO {

	public void criarTabelas() throws SQLException {
		String sqlCategorias =
			"CREATE TABLE IF NOT EXISTS categorias (\n" +
			"	id INTEGER NULL,\n" +
			"	nome_categoria varchar(48) NOT NULL,\n" +
			"	CONSTRAINT categorias_pk PRIMARY KEY (id)\n" +
			");";

		String sqlTags =
			"CREATE TABLE IF NOT EXISTS tags (\n" +
			"	id INTEGER NULL,\n" +
			"	nome_tag varchar(48) NOT NULL,\n" +
			"	CONSTRAINT tags_pk PRIMARY KEY (id)\n" +
			");";

		String sqlArquivos =
			"CREATE TABLE IF NOT EXISTS arquivos (\n" +
			"	id INTEGER NULL,\n" +
			"	url varchar NOT NULL,\n" +
			"	mime varchar NOT NULL,\n" +
			"	tamanho integer NOT NULL,\n" +
			"	CONSTRAINT arquivos_pk PRIMARY KEY (id)\n" +
			");";

		String sqlUsuarios =
			"CREATE TABLE IF NOT EXISTS usuarios (\n" +
			"	id INTEGER NULL,\n" +
			"	nome_usuario varchar(128) NOT NULL,\n" +
			"	email varchar(128) NOT NULL,\n" +
			"	senha_hash varchar NOT NULL,\n" +
			"	data_ultimo_login timestamp NULL DEFAULT '',\n" +
			"	id_foto_arquivo INTEGER NULL DEFAULT '0',\n" +
			"	deletado boolean NULL DEFAULT 'false',\n" +
			"	CONSTRAINT usuarios_pk PRIMARY KEY (id),\n" +
			"	CONSTRAINT usuarios_fk FOREIGN KEY (id_foto_arquivo) "
				+ "REFERENCES arquivos(id)\n" +
			");";

		String sqlTokens =
			"CREATE TABLE IF NOT EXISTS tokens (\n" +
			"	id INTEGER NULL,\n" +
			"	token varchar NOT NULL,\n" +
			"	data_criacao timestamp NOT NULL,\n" +
			"	id_usuario INTEGER NOT NULL,\n" +
			"	CONSTRAINT tokens_pk PRIMARY KEY (id),\n" +
			"	CONSTRAINT tokens_fk FOREIGN KEY (id_usuario) "
				+ "REFERENCES usuarios(id)\n" +
			");";

		String sqlTarefas =
			"CREATE TABLE IF NOT EXISTS tarefas (\n" +
			"	id INTEGER NULL,\n" +
			"	titulo varchar(128) NOT NULL,\n" +
			"	descricao varchar(8192) NULL DEFAULT '',\n" +
			"	cor char(7) NULL DEFAULT '',\n" +
			"	prioridade integer NULL DEFAULT '',\n" +
			"	data_criacao timestamp NOT NULL,\n" +
			"	data_concluida timestamp NULL DEFAULT '',\n" +
			"	data_limite timestamp NULL DEFAULT '',\n" +
			"	CONSTRAINT tarefas_pk PRIMARY KEY (id)\n" +
			");";

		String sqlTarefas_tags =
			"CREATE TABLE IF NOT EXISTS tarefas_tags (\n" +
			"	id_tarefa INTEGER NOT NULL,\n" +
			"	id_tag INTEGER NOT NULL,\n" +
			"	CONSTRAINT tarefas_tags_fk FOREIGN KEY (id_tarefa) "
				+ "REFERENCES tarefas(id),\n" +
			"	CONSTRAINT tarefas_tags_fk_1 FOREIGN KEY (id_tag) "
				+ "REFERENCES tags(id)\n" +
			");";

		String sqlTarefas_categorias =
			"CREATE TABLE IF NOT EXISTS tarefas_categorias (\n" +
			"	id_tarefa INTEGER NOT NULL,\n" +
			"	id_categoria INTEGER NOT NULL,\n" +
			"	CONSTRAINT tarefas_categorias_fk FOREIGN KEY (id_tarefa) "
				+ "REFERENCES tarefas(id),\n" +
			"	CONSTRAINT tarefas_categorias_fk_1 FOREIGN KEY (id_categoria) "
				+ "REFERENCES categorias(id)\n" +
			");";

		String sqlTarefas_usuarios =
			"CREATE TABLE IF NOT EXISTS tarefas_usuarios (\n" +
			"	id_tarefa INTEGER NOT NULL,\n" +
			"	id_usuario INTEGER NOT NULL,\n" +
			"	permissoes bit(16) NULL DEFAULT '0',\n" +
			"	CONSTRAINT tarefas_usuarios_fk FOREIGN KEY (id_tarefa) "
				+ "REFERENCES tarefas(id),\n" +
			"	CONSTRAINT tarefas_usuarios_fk_1 FOREIGN KEY (id_usuario) "
				+ "REFERENCES usuarios(id)\n" +
			");";

		String sqlTarefas_arquivos =
			"CREATE TABLE IF NOT EXISTS tarefas_arquivos (\n" +
			"	id_tarefa INTEGER NOT NULL,\n" +
			"	id_arquivo INTEGER NOT NULL,\n" +
			"	CONSTRAINT tarefas_arquivos_fk FOREIGN KEY (id_tarefa) "
					+ "REFERENCES tarefas(id),\n" +
			"	CONSTRAINT tarefas_arquivos_fk_1 FOREIGN KEY (id_arquivo) "
					+ "REFERENCES arquivos(id)\n" +
			");";

		// Categoria padrão para todas as tarefas
		// Não será mais criada dessa forma quando houver mais de um usuário.
		String sqlCategoriaPadrao =
			"INSERT INTO categorias (id, nome_categoria) VALUES (1, 'geral');";

		BdAcesso bd = BdAcesso.abrirConexao();

		bd.stmt.addBatch(sqlCategorias);
		bd.stmt.addBatch(sqlTags);
		bd.stmt.addBatch(sqlArquivos);
		bd.stmt.addBatch(sqlUsuarios);
		bd.stmt.addBatch(sqlTokens);
		bd.stmt.addBatch(sqlTarefas);
		bd.stmt.addBatch(sqlTarefas_tags);
		bd.stmt.addBatch(sqlTarefas_categorias);
		bd.stmt.addBatch(sqlTarefas_usuarios);
		bd.stmt.addBatch(sqlTarefas_arquivos);
		bd.stmt.addBatch(sqlCategoriaPadrao);

		bd.stmt.executeBatch();
		bd.fecharConexao();
	}
}
