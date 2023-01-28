package todoso.backend.dados;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TarefaDTO extends BaseDTO {

	@NotEmpty(message="Title should not be empty")
	@Size(max=128, message="Title should not be more than 128 characters long")
	@JsonProperty("title")
	private String titulo;

	@Positive
	@JsonProperty("id")
	private Long id;

	@Size(max=8192,
		message="Description should not be more than 8192 characters long")
	@JsonProperty("description")
	private String descricao;

	@NotNull(message="CreationDate cannot be null")
	@PastOrPresent(message="CreationDate cannot be set on the future")
	@JsonProperty("creationDate")
	private Timestamp dataCriacao;

	// TODO: criar validador para intervalos de data
	// TODO: garantir que dataConcluida >= dataCriacao
	@JsonProperty("completionDate")
	private Timestamp dataConcluida;

	// TODO: garantir que dataLimite > dataCriacao
	@Future(message="Deadline cannot be set on the past")
	@JsonProperty("deadline")
	private Timestamp dataLimite;

	@JsonProperty("categories")
	private ArrayList<@NotNull CategoriaDTO> categorias;

	@JsonProperty("tags")
	private ArrayList<@NotNull TagDTO> tags;

	@NotNull
	@Max(value=7, message="Priority should not be higher than 7")
	@Min(value=-7, message="Priority should not be lower than 7")
	@JsonProperty("priority")
	private Integer prioridade;

	@Pattern(regexp="^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$",
		message="Color should be in hexadecimal pattern, as in #AF90FF or #FFF")
	@JsonProperty("color")
	private String cor;

	@JsonProperty("files")
	private ArrayList<@NotNull String> arquivos;

	@JsonProperty("users")
	private ArrayList<@NotNull UsuarioDTO> usuarios;

	public TarefaDTO() {
		setId(null);
		setTitulo(null);
		setDescricao(null);
		setDataCriacao(null);
		setDataConcluida(null);
		setDataLimite(null);
		setCategorias(null);
		setTags(null);
		setPrioridade(null);
		setCor(null);
		setArquivos(null);
		setUsuarios(null);
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Timestamp getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataConcluida(Timestamp dataConcluida) {
		this.dataConcluida = dataConcluida;
	}

	public Timestamp getDataConcluida() {
		return this.dataConcluida;
	}

	public void setDataLimite(Timestamp dataLimite) {
		this.dataLimite = dataLimite;
	}

	/**
	 * @return data limite da tarefa ou null. */
	public Timestamp getDataLimite() {
		return this.dataLimite;
	}

	public void setCategorias(ArrayList<CategoriaDTO> list)	 {
		this.categorias = list;
	}

	public ArrayList<CategoriaDTO> getCategorias() {
		return this.categorias;
	}

	public void setTags(ArrayList<TagDTO> list) {
		this.tags = list;
	}

	public ArrayList<TagDTO> getTags() {
		return this.tags;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}

	public Integer getPrioridade() {
		return this.prioridade;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getCor() {
		return this.cor;
	}

	public void setArquivos(ArrayList<String> list) {
		this.arquivos = list;
	}

	public ArrayList<String> getArquivos() {
		return this.arquivos;
	}

	public void setUsuarios(ArrayList<UsuarioDTO> list) {
		this.usuarios = list;
	}

	public ArrayList<UsuarioDTO> getUsuarios() {
		return this.usuarios;
	}

	public boolean dataConcluidaEhMaiorQueDataCriacao() {
		return (this.dataConcluida.after(this.dataCriacao));
	}
}