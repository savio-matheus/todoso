package todoso.backend.dados;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public class TagDTO extends BaseDTO {

	public TagDTO() {}

	public TagDTO(long id, String nome_tag) {
		this.id = id;
		this.nome = nome_tag;
	}

	@Positive
	@JsonProperty("id")
	private Long id;

	@NotEmpty(message="Name should not be empty or null")
	@Size(max=48, message="Name should not be more than 48 characters long")
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