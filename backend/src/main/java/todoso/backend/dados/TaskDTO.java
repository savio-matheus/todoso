package todoso.backend.dados;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class TaskDTO {

	@JsonProperty("title")
	private String titulo;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("description")
	private String descricao;
	
	@JsonProperty("creationDate")
	private Timestamp dataCriacao;
	
	@JsonProperty("completionDate")
	private Timestamp dataConcluida;
	
	@JsonProperty("deadline")
	private Timestamp dataLimite;
	
	@JsonProperty("categories")
	private ArrayList<CategoryDTO> categorias;
	
	@JsonProperty("tags")
	private ArrayList<TagDTO> tags;
	
	@JsonProperty("priority")
	private Integer prioridade;
	
	@JsonProperty("color")
	private String cor; // #FFFFFF
	
	@JsonProperty("files")
	private ArrayList<String> arquivos;
	
	@JsonProperty("users")
	private ArrayList<UserDTO> usuarios;

	public TaskDTO() {
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

	public void setCategorias(ArrayList<CategoryDTO> list)	 {
		this.categorias = list;
	}

	public ArrayList<CategoryDTO> getCategorias() {
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

	public void setUsuarios(ArrayList<UserDTO> list) {
		this.usuarios = list;
	}

	public ArrayList<UserDTO> getUsuarios() {
		return this.usuarios;
	}
}