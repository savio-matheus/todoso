package todoso.backend.servico;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import todoso.backend.dados.Olar;

public class OlarServico {
	public ResponseEntity<String> criarBancoDeDados() {
		try {
			new Olar().criarTabelas();
		}
		catch (SQLException e) {
			return new ResponseEntity<>("Erro ao criar/verificar as tabelas",
				HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(String.format("Olá, Mundo!"),
			HttpStatus.OK);
	}
}