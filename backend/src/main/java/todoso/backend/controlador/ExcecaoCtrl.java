package todoso.backend.controlador;

import java.lang.IllegalArgumentException;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import todoso.backend.excecoes.InvalidRequestException;
import todoso.backend.excecoes.NotFoundException;

@ControllerAdvice
public class ExcecaoCtrl extends ResponseEntityExceptionHandler {

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<Resposta<String>> retornoNotFoundException(Exception ex, WebRequest req) {
		ex.printStackTrace();

		Resposta<String> conteudo = new Resposta<>();
		conteudo.setHttp(HttpStatus.NOT_FOUND);
		conteudo.addDadosRetorno(ex.getLocalizedMessage());

		return new ResponseEntity<>(conteudo, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({InvalidRequestException.class})
	public ResponseEntity<Resposta<String>> retornoInvalidRequestException(Exception ex, WebRequest req) {
		ex.printStackTrace();

		Resposta<String> conteudo = new Resposta<>();
		conteudo.setHttp(HttpStatus.BAD_REQUEST);
		conteudo.addDadosRetorno(ex.getLocalizedMessage());

		return new ResponseEntity<>(conteudo, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({
		JsonProcessingException.class,
		IllegalArgumentException.class})
	public ResponseEntity<Resposta<String>> retornoJsonProcessingException(Exception ex, WebRequest req) {
		ex.printStackTrace();

		Resposta<String> conteudo = new Resposta<>();
		conteudo.setHttp(HttpStatus.NOT_FOUND);
		conteudo.addDadosRetorno(ex.getLocalizedMessage());

		return new ResponseEntity<>(conteudo, HttpStatus.BAD_REQUEST);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, 
        	HttpStatus status, WebRequest request) {
		ex.printStackTrace();

		Resposta<String> conteudo = new Resposta<>();

		for (FieldError e : ex.getBindingResult().getFieldErrors()) {
			@SuppressWarnings("null") // É feita a verificação
			String rejectedValue = (e.getRejectedValue() == null) ?
				"null" : e.getRejectedValue().toString();

			if (rejectedValue.length() > 128) {
				rejectedValue = rejectedValue.substring(0, 128) + "...";
			}

			conteudo.addDadosRetorno(
				"[" + e.getDefaultMessage() +
				" (value: " + rejectedValue + ")]"
			);
		}

		conteudo.setHttp(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(conteudo, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Resposta<String>> retornoException(Exception ex, WebRequest req) {
		ex.printStackTrace();

		Resposta<String> conteudo = new Resposta<>();
		conteudo.setHttp(HttpStatus.INTERNAL_SERVER_ERROR);
		conteudo.addDadosRetorno("Exception: " + ex.getLocalizedMessage());

		return new ResponseEntity<>(conteudo, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
