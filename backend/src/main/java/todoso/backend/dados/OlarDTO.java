package todoso.backend.dados;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OlarDTO extends BaseDTO {
	
	@JsonProperty("name")
	private final String nome;

	public OlarDTO(String nome) {
		this.nome = nome;
	}

	public String getnome() {
		return (this.nome == null) ? "" : this.nome;
	}
}