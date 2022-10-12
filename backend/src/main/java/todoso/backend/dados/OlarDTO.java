package todoso.backend.dados;

public class OlarDTO {
	private final String nome;

	public OlarDTO(String nome) {
		this.nome = nome;
	}

	public String getnome() {
		return (this.nome == null) ? "" : this.nome;
	}
}