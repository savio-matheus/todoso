package todoso.backend.controlador;

import java.util.HashMap;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class ErroCtrl implements ErrorController {
	@RequestMapping(
		value="/error",
		method={RequestMethod.GET, RequestMethod.POST},
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> getErroGenerico() {
		HashMap<String, Object> retorno = new HashMap<>();
		retorno.put("status", Status.novo(HttpStatus.INTERNAL_SERVER_ERROR, "Erro desconhecido", ""));
		return new ResponseEntity<>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}