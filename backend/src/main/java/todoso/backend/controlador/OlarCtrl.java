package todoso.backend.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import todoso.backend.servico.OlarServico;

@Hidden
@RestController()
class OlarCtrl {

	@GetMapping("/api/v1/olar")
	public ResponseEntity<String> olar() {
		OlarServico s = new OlarServico();
		return s.criarBancoDeDados();
	}
}