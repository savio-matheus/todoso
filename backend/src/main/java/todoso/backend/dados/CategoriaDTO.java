package todoso.backend.dados;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public class CategoriaDTO extends BaseDTO {

	public CategoriaDTO() {}

	public CategoriaDTO(long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	@Positive
	@JsonProperty("id")
	protected Long id;

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