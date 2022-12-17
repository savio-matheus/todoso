package todoso.backend.controlador;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {

	@JsonProperty("code")
	public int codigo;

	@JsonProperty("message")
	public String mensagem;

	public static Status novo(int codigo, String mensagem) {
		Status s = new Status();

		s.codigo = codigo;
		s.mensagem = mensagem;

		return s;
	}
}
