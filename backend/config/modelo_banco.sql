-- Testado no Postgresql

CREATE TABLE IF NOT EXISTS categorias (
	id serial NOT NULL,
	nome_categoria varchar(48) NOT NULL,
	CONSTRAINT categorias_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tags (
	id serial NOT NULL,
	nome_tag varchar(48) NOT NULL,
	CONSTRAINT tags_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS arquivos (
	id serial NOT NULL,
	url varchar NOT NULL,
	mime varchar NOT NULL,
	tamanho integer NULL,
	CONSTRAINT arquivos_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS usuarios (
	id serial NOT NULL,
	nome_usuario varchar(128) NOT NULL,
	email varchar(128) NOT NULL,
	senha_hash varchar NOT NULL,
	data_ultimo_login timestamp NULL,
	id_foto_arquivo serial,
	deletado boolean NULL,
	CONSTRAINT usuarios_pk PRIMARY KEY (id),
	CONSTRAINT usuarios_fk FOREIGN KEY (id_foto_arquivo) REFERENCES arquivos(id)
);

CREATE TABLE IF NOT EXISTS tokens (
	id serial NOT NULL,
	token varchar NOT NULL,
	data_criacao timestamp NOT NULL,
	id_usuario serial NOT NULL,
	CONSTRAINT tokens_pk PRIMARY KEY (id),
	CONSTRAINT tokens_fk FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS tarefas (
	id serial NOT NULL,
	titulo varchar(128) NOT NULL,
	descricao varchar(8192) NULL,
	cor char(7) NULL,
	prioridade integer NULL,
	data_criacao timestamp NOT NULL,
	data_concluida timestamp NULL,
	data_limite timestamp NULL,
	CONSTRAINT tarefas_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tarefas_tags (
	id_tarefa serial NOT NULL,
	id_tag serial NOT NULL,
	CONSTRAINT tarefas_tags_fk FOREIGN KEY (id_tarefa) REFERENCES tarefas(id),
	CONSTRAINT tarefas_tags_fk_1 FOREIGN KEY (id_tag) REFERENCES tags(id)
);

CREATE TABLE IF NOT EXISTS tarefas_categorias (
	id_tarefa serial NOT NULL,
	id_categoria serial NOT NULL,
	CONSTRAINT tarefas_categorias_fk FOREIGN KEY (id_tarefa) REFERENCES tarefas(id),
	CONSTRAINT tarefas_categorias_fk_1 FOREIGN KEY (id_categoria) REFERENCES categorias(id)
);

CREATE TABLE IF NOT EXISTS tarefas_usuarios (
	id_tarefa serial NOT NULL,
	id_usuario serial NOT NULL,
	permissoes bit(16) NULL,
	CONSTRAINT tarefas_usuarios_fk FOREIGN KEY (id_tarefa) REFERENCES tarefas(id),
	CONSTRAINT tarefas_usuarios_fk_1 FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS tarefas_arquivos (
	id_tarefa serial NOT NULL,
	id_arquivo serial NOT NULL,
	CONSTRAINT tarefas_arquivos_fk FOREIGN KEY (id_tarefa) REFERENCES tarefas(id),
	CONSTRAINT tarefas_arquivos_fk_1 FOREIGN KEY (id_arquivo) REFERENCES arquivos(id)
);

