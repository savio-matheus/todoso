-- SQLite3

CREATE TABLE IF NOT EXISTS categorias (
	id integer NULL,
	nome_categoria varchar(48) NOT NULL,
	CONSTRAINT categorias_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tags (
	id integer NULL,
	nome_tag varchar(48) NOT NULL,
	CONSTRAINT tags_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS arquivos (
	id integer NULL,
	url varchar NOT NULL,
	mime varchar NOT NULL,
	tamanho integer NOT NULL,
	CONSTRAINT arquivos_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS usuarios (
	id integer NULL,
	nome_usuario varchar(128) NOT NULL,
	email varchar(128) NOT NULL,
	senha_hash varchar NOT NULL,
	data_ultimo_login timestamp NULL DEFAULT '',
	id_foto_arquivo integer NULL DEFAULT '0',
	deletado boolean NULL DEFAULT 'false',
	CONSTRAINT usuarios_pk PRIMARY KEY (id),
	CONSTRAINT usuarios_fk FOREIGN KEY (id_foto_arquivo) REFERENCES arquivos(id)
);

CREATE TABLE IF NOT EXISTS tokens (
	id integer NULL,
	token varchar NOT NULL,
	data_criacao timestamp NOT NULL,
	id_usuario integer NOT NULL,
	CONSTRAINT tokens_pk PRIMARY KEY (id),
	CONSTRAINT tokens_fk FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS tarefas (
	id integer NULL,
	titulo varchar(128) NOT NULL,
	descricao varchar(8192) NULL DEFAULT '',
	cor char(7) NULL DEFAULT '',
	prioridade integer NULL DEFAULT '',
	data_criacao timestamp NOT NULL,
	data_concluida timestamp NULL DEFAULT '',
	data_limite timestamp NULL DEFAULT '',
	CONSTRAINT tarefas_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tarefas_tags (
	id_tarefa integer NOT NULL,
	id_tag integer NOT NULL,
	CONSTRAINT tarefas_tags_fk FOREIGN KEY (id_tarefa) REFERENCES tarefas(id),
	CONSTRAINT tarefas_tags_fk_1 FOREIGN KEY (id_tag) REFERENCES tags(id)
);

CREATE TABLE IF NOT EXISTS tarefas_categorias (
	id_tarefa integer NOT NULL,
	id_categoria integer NOT NULL,
	CONSTRAINT tarefas_categorias_fk FOREIGN KEY (id_tarefa) REFERENCES tarefas(id),
	CONSTRAINT tarefas_categorias_fk_1 FOREIGN KEY (id_categoria) REFERENCES categorias(id)
);

CREATE TABLE IF NOT EXISTS tarefas_usuarios (
	id_tarefa integer NOT NULL,
	id_usuario integer NOT NULL,
	permissoes bit(16) NULL DEFAULT '0',
	CONSTRAINT tarefas_usuarios_fk FOREIGN KEY (id_tarefa) REFERENCES tarefas(id),
	CONSTRAINT tarefas_usuarios_fk_1 FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS tarefas_arquivos (
	id_tarefa integer NOT NULL,
	id_arquivo integer NOT NULL,
	CONSTRAINT tarefas_arquivos_fk FOREIGN KEY (id_tarefa) REFERENCES tarefas(id),
	CONSTRAINT tarefas_arquivos_fk_1 FOREIGN KEY (id_arquivo) REFERENCES arquivos(id)
);

