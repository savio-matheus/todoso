package todoso.backend.controlador;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpStatus;

public class Status {

	@JsonProperty("http")
	public HttpStatus http;

	@JsonProperty("description")
	public String descri;

	@JsonProperty("message")
	public String mensagem;

	public static Status novo(HttpStatus codigo, String descri, String mensagem) {
		Status s = new Status();

		s.descri = descri;
		s.http = codigo;
		s.mensagem = mensagem;

		return s;
	}
}
