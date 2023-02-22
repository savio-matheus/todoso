package todoso.backend;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(
	classes=todoso.backend.controlador.Todoso.class,
	webEnvironment = WebEnvironment.RANDOM_PORT
)
class OlarTest {

	@LocalServerPort
	private int porta;

	@Autowired
	private TestRestTemplate restTemplate;

	// Teste desativado
	/*@Test
	void primeiraInicializacao() {
		assertThat(
			this.restTemplate.getForObject(
				"http://localhost:"+ porta +"/api/v1/olar", String.class
			)
		).contains("Olá, Mundo!");
	}*/

}
