package todoso.backend.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
public class AutenticacaoCtrl {
	public ResponseEntity<Object> postAutenticacao() {
		return null;
	}
}