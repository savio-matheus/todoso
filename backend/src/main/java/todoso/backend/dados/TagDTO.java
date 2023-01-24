package todoso.backend.dados;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagDTO extends BaseDTO {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	private String nome;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}