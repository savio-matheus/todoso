package todoso.backend.controlador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import todoso.backend.dados.TarefaDTO;
import todoso.backend.dados.Tarefas;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
class TarefaAPIv1 {
	Tarefas dados = new Tarefas();

	@RequestMapping(
			value = "/api/v1/tasks",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<String> getTarefas() {
		return getTarefas(null);
	}

	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<String> getTarefas(@PathVariable("id") Long id) {
		HashMap<Object, Object> retorno = new HashMap<>();
		ArrayList<TarefaDTO> lista = null;

		try {
			TarefaDTO filtro = new TarefaDTO();
			filtro.setId(id);
			lista = dados.listar(filtro);
		}
		catch (SQLException e) {
			retorno.put("status", "Internal error: SQLException");
			e.printStackTrace();
			return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		retorno.put("status", "sucess");
		retorno.put("data", lista);

		return new ResponseEntity(retorno, HttpStatus.FOUND);
	}

	@RequestMapping(
			value = "/api/v1/tasks",
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<String> postTarefas(@RequestBody String json) {
		ArrayList<TarefaDTO> tarefas = new ArrayList<>();
		ObjectMapper om = new ObjectMapper();
		HashMap<Object, Object> retorno = new HashMap<>();

		try {
			tarefas.add(om.readValue(json, TarefaDTO.class));
			dados.inserir(tarefas);
			retorno.put("status", "Accepted");
		} catch (JsonProcessingException e) {
			retorno.put("status", "Error: JsonProcessingException");
			//e.printStackTrace();
			return new ResponseEntity(retorno, HttpStatus.BAD_REQUEST);
		} catch (SQLException ex) {
			retorno.put("status", "Internal error: SQLException");
			ex.printStackTrace();
			return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		retorno.put("json", tarefas.get(0).getTitulo());

		return new ResponseEntity(retorno, HttpStatus.ACCEPTED);
	}

	@RequestMapping(
			value = "/api/v1",
			method = {RequestMethod.PATCH},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<String> patchTarefas(@RequestBody String json) {
		return patchTarefas(null, json);
	}

	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = {RequestMethod.PATCH},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<String> patchTarefas(@PathVariable("id") Long id,
			@RequestBody String json) {

		ArrayList<TarefaDTO> tarefas = new ArrayList<>();
		ObjectMapper om = new ObjectMapper();
		HashMap<Object, Object> retorno = new HashMap<>();
		TarefaDTO t;

		try {
			t = om.readValue(json, TarefaDTO.class);
			if (id != null) {
				t.setId(id);
			}
			tarefas.add(t);
			dados.atualizar(tarefas);
			retorno.put("status", "sucess");
		}
		catch (JsonProcessingException e) {
			retorno.put("status", "Error: JsonProcessingException");
			return new ResponseEntity(retorno, HttpStatus.BAD_REQUEST);
		}
		catch (SQLException e) {
			retorno.put("status", "Internal Error: SQLException");
			return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity(retorno, HttpStatus.ACCEPTED);
	}

	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = {RequestMethod.DELETE},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<String> deleteTarefas(@PathVariable("id") Long id) {
		TarefaDTO filtro = new TarefaDTO();
		HashMap<Object, Object> retorno = new HashMap<>();

		filtro.setId(id);
		try {
			dados.excluir(filtro);
			retorno.put("status", "Deleted");
			return new ResponseEntity(retorno, HttpStatus.ACCEPTED);
		} catch (SQLException ex) {
			retorno.put("status", "Internal error: SQLException");
			return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}