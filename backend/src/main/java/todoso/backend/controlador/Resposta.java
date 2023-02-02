package todoso.backend.controlador;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Resposta<T> {

	@JsonProperty("http_status")
	private HttpStatus http;

	@JsonProperty("id")
	private long id;

	@JsonProperty("data")
	private ArrayList<T> dadosRetorno;

	public HttpStatus getHttp() {
		return http;
	}

	public void setHttp(HttpStatus http) {
		this.http = http;
	}

	public ArrayList<T> getDadosRetorno() {
		return dadosRetorno;
	}

	public void setDadosRetorno(ArrayList<T> dadosRetorno) {
		this.dadosRetorno = dadosRetorno;
	}

	public void addDadosRetorno(T dado) {
		if (this.dadosRetorno == null) {
			this.dadosRetorno = new ArrayList<T>();
		}

		this.dadosRetorno.add(dado);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



}