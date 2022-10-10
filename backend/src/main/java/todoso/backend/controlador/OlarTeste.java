package todoso.backend.controlador;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
class OlarTeste {
	private static final String MODELO_RESPOSTA = "Olá, %s!";

	@GetMapping("/olar")
	public Olar olar(
		@RequestParam(value = "name", defaultValue = "Mundo") String nome) {

		return new Olar(String.format(MODELO_RESPOSTA, nome));
	}
}