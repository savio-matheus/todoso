package todoso.backend.controlador;

import java.lang.IllegalArgumentException;
import java.util.HashMap;

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

import todoso.backend.excecoes.NotFoundException;

@ControllerAdvice
public class ExcecaoCtrl extends ResponseEntityExceptionHandler {

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<Object> retornoNotFoundException(Exception ex, WebRequest req) {
		ex.printStackTrace();
		Status s = Status.novo(
			HttpStatus.NOT_FOUND,
			ex.getLocalizedMessage()
		);
		HashMap<String, Status> conteudo = new HashMap<>();
		conteudo.put("status", s);

		return new ResponseEntity<>(conteudo, s.http);
	}

	@ExceptionHandler({
		JsonProcessingException.class,
		IllegalArgumentException.class})
	public ResponseEntity<Object> retornoJsonProcessingException(Exception ex, WebRequest req) {
		ex.printStackTrace();

		Status s = Status.novo(
			HttpStatus.BAD_REQUEST,
			ex.getLocalizedMessage()
		);
		HashMap<String, Status> conteudo = new HashMap<>();
		conteudo.put("status", s);

		return new ResponseEntity<>(conteudo, s.http);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, 
        	HttpStatus status, WebRequest request) {
		ex.printStackTrace();

		StringBuffer erros = new StringBuffer();

		for (FieldError e : ex.getBindingResult().getFieldErrors()) {
			String rejectedValue = (e.getRejectedValue() == null) ?
				"null" : e.getRejectedValue().toString();

			if (rejectedValue.length() > 128) {
				rejectedValue = rejectedValue.substring(0, 128) + "...";
			}
			erros.append(
				"[" + e.getDefaultMessage() +
				" (value: " + rejectedValue + ")]"
			);
		}

		Status s = Status.novo(
			HttpStatus.BAD_REQUEST,
			erros.toString()
		);
		HashMap<String, Status> conteudo = new HashMap<>();
		conteudo.put("status", s);

		return new ResponseEntity<>(conteudo, s.http);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> retornoException(Exception ex, WebRequest req) {
		ex.printStackTrace();
		Status s = Status.novo(
			HttpStatus.INTERNAL_SERVER_ERROR,
			"Exception: " + ex.getLocalizedMessage()
		);

		HashMap<String, Status> conteudo = new HashMap<>();
		conteudo.put("status", s);

		return new ResponseEntity<>(conteudo, s.http);
	}
}
