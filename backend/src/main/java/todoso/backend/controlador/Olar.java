package todoso.backend.controlador;

import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
class Olar {
	private static final String MODELO_RESPOSTA = "Olá, %s!";

	@GetMapping("/api/v1/olar")
	public ResponseEntity olar(
		@RequestParam(value = "name", defaultValue = "Mundo") String nome) {
		
		try {
			todoso.backend.dados.Olar.criarTabelas();
		}
		catch (SQLException e) {
			return new ResponseEntity<String>("Erro ao criar/verificar as tabelas",
				HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity(String.format(MODELO_RESPOSTA, nome),
			HttpStatus.OK);
	}
}