package todoso.backend.controlador;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import todoso.backend.servico.OlarServico;

@RestController()
class OlarCtrl {

	@GetMapping("/api/v1/olar")
	public ResponseEntity<String> olar() {
		OlarServico s = new OlarServico();
		return s.criarBancoDeDados();
	}
}