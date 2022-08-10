package backend.persistencia;

import java.sql.SQLException;

import backend.app.Tarefa;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.NamingException;

/**
 *
 * @author mestre
 */
public class TarefasPersistencia {

	private Tarefa tarefa;
	private String titulo;
	private String descricao;
	private String dataCriacao;
	private String dataConcluida;
	private String dataLimite;
	private String cor;
	private String prioridade;
	
	// TODO: relacionar tabelas de tags, de categorias e de arquivos
	public TarefasPersistencia(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	
	public Integer escreva() throws SQLException {
		preencherCampos();
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tarefas")
			.append("(titulo, descricao, data_criacao, data_concluida, ")
			.append("data_limite, cor, prioridade)")
			.append("VALUES(")
			.append(titulo).append(", ")
			.append(descricao).append(", ")
			.append(dataCriacao).append(", ")
			.append(dataConcluida).append(", ")
			.append(dataLimite).append(", ")
			.append(cor).append(", ")
			.append(prioridade)
			.append(");");
		
		try (Connection conn = Persistencia.abreConexaoBD()) {
			Integer id = conn.createStatement().executeUpdate(sql.toString(),
				Statement.RETURN_GENERATED_KEYS);
			conn.close();
			return id;
		} catch (NamingException e) {
			System.out.println(e);
			throw new SQLException();
		}
	}

	public static Tarefa leia(Integer id) throws SQLException {
		ResultSet rs;
		Boolean encontrado;
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT id, titulo, descricao, data_criacao, ")
			.append("data_concluida, data_limite, cor, prioridade")
			.append("FROM tarefas WHERE id=\'")
			.append(id)
			.append("\';");

		try (Connection conn = Persistencia.abreConexaoBD()) {
			Statement s = conn.createStatement();
			encontrado = s.execute(sql.toString());
			rs = s.getResultSet();
		} catch (NamingException e) {
			System.out.println(e);
			throw new SQLException();
		}
		
		if (!encontrado) {
			return null;
		}
		
		rs.first();
		return new Tarefa(
			rs.getString(rs.findColumn("titulo")),
			rs.getInt(rs.findColumn("id")),
			rs.getString(rs.findColumn("descricao")),
			rs.getString(rs.findColumn("data_criacao")),
			rs.getString(rs.findColumn("data_concluida")),
			rs.getString(rs.findColumn("data_limite")),
			null,
			null,
			rs.getInt(rs.findColumn("prioridade")),
			rs.getString(rs.findColumn("cor"))
		);
	}
	
	private void preencherCampos() {
		titulo = "\'" + tarefa.getTitle() + "\'";
		descricao = "\'" + tarefa.getDescription() + "\'";
		dataCriacao = "\'" + tarefa.getCreationDate() + "\'";
		dataConcluida = "\'" + tarefa.getCompletionDate() + "\'";
		dataLimite = "\'" + tarefa.getDeadline() + "\'";
		cor = "\'" + tarefa.getColor() + "\'";
		prioridade = "\'" + tarefa.getPriority() + "\'";
	}
}
