package todoso.backend.controlador;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpStatus;

public class Status {

	@JsonProperty("http")
	public HttpStatus http;

	@JsonProperty("message")
	public String mensagem;

	public static Status novo(HttpStatus codigo, String mensagem) {
		Status s = new Status();

		s.http = codigo;
		s.mensagem = mensagem;

		return s;
	}
}
