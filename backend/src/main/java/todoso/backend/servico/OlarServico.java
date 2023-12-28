package todoso.backend.servico;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import todoso.backend.dados.BdAcesso;
import todoso.backend.dados.OlarDAO;

public class OlarServico {
	public ResponseEntity<String> criarBancoDeDados() throws SQLException {
		BdAcesso bancoDeDados = null;

		bancoDeDados = BdAcesso.abrirConexao(false);

		try {
			new OlarDAO(bancoDeDados).criarTabelas();
			bancoDeDados.fecharConexao();
		}
		catch (SQLException e) {
			e.printStackTrace();
			bancoDeDados.reverter();
			bancoDeDados.fecharConexao();
			return new ResponseEntity<>(
				"Erro ao criar/verificar as tabelas (talvez já existam).",
				HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(String.format("Olá, Mundo!"),
			HttpStatus.OK);
	}
}