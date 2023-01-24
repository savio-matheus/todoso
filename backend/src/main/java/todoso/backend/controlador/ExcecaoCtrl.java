package todoso.backend.controlador;

import java.lang.IllegalArgumentException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import todoso.backend.excecoes.NotFoundException;

@ControllerAdvice
public class ExcecaoCtrl extends ResponseEntityExceptionHandler {

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<Object> retornoNotFoundException(Exception ex, WebRequest req) {
		Status s = Status.novo(
			HttpStatus.NOT_FOUND,
			ex.getLocalizedMessage()
		);
		HashMap<String, Status> conteudo = new HashMap<>();
		conteudo.put("status", s);

		return new ResponseEntity<>(conteudo, s.http);
	}

	@ExceptionHandler({JsonProcessingException.class, IllegalArgumentException.class})
	public ResponseEntity<Object> retornoJsonProcessingException(Exception ex, WebRequest req) {
		Status s = Status.novo(
			HttpStatus.BAD_REQUEST,
			ex.getLocalizedMessage()
		);
		HashMap<String, Status> conteudo = new HashMap<>();
		conteudo.put("status", s);

		return new ResponseEntity<>(conteudo, s.http);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> retornoException(Exception ex, WebRequest req) {
		Status s = Status.novo(
			HttpStatus.INTERNAL_SERVER_ERROR,
			ex.getLocalizedMessage()
		);

		HashMap<String, Status> conteudo = new HashMap<>();
		conteudo.put("status", s);

		return new ResponseEntity<>(conteudo, s.http);
	}
}
