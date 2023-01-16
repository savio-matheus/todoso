package todoso.backend.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;

import todoso.backend.dados.TarefaDTO;
import todoso.backend.servico.TarefaServico;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
class TarefaCtrl {
	private HashMap<String, Object> retorno = new HashMap<>();
	private TarefaDTO filtros = new TarefaDTO();
	private ArrayList<TarefaDTO> lista = new ArrayList<>();
	private ObjectMapper conversor = new ObjectMapper();

	private TarefaDTO tarDTO = null;
	private TarefaServico servico = new TarefaServico();

	private static final Status statusOk = Status.novo(HttpStatus.OK, "OK", "");
	private static final Status statusCreated = Status.novo(HttpStatus.CREATED, "CREATED", "");
	private static final Status statusAccepted = Status.novo(HttpStatus.ACCEPTED, "ACCEPTED", "");

	@RequestMapping(
			value = "/api/v1/tasks",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> getTarefa() throws Exception {
		return getTarefa(null);
	}

	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> getTarefa(@PathVariable("id") Long id) throws Exception {
		filtros.setId(id);
		lista = servico.selecionarTarefas(filtros);

		retorno.put("status", statusOk);
		retorno.put("data", lista);

		return new ResponseEntity<>(retorno, statusOk.http);
	}

	@RequestMapping(
			value = "/api/v1/tasks",
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> postTarefas(@RequestBody String json) throws Exception {
		long id = servico.criarTarefa(conversor.readValue(json, TarefaDTO.class)).getId();

		retorno.put("status", statusCreated);
		retorno.put("id", id);

		return new ResponseEntity<>(retorno, HttpStatus.ACCEPTED);
	}

	@RequestMapping(
			value = "/api/v1",
			method = {RequestMethod.PATCH},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Object> patchTarefas(@RequestBody String json) throws Exception {
		return patchTarefas(null, json);
	}

	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = {RequestMethod.PATCH},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Object> patchTarefas(@PathVariable("id") Long id,
			@RequestBody String json) throws Exception {

		tarDTO = conversor.readValue(json, TarefaDTO.class);
		tarDTO.setId(id);

		servico.editarTarefa(tarDTO);
		retorno.put("status", statusOk);

		return new ResponseEntity<>(retorno, statusOk.http);
	}

	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = {RequestMethod.DELETE},
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Object> deleteTarefas(@PathVariable("id") Long id) throws Exception {

		filtros.setId(id);

		servico.deletarTarefa(filtros);
		retorno.put("status", statusAccepted);
		retorno.put("id", id);

		return new ResponseEntity<>(retorno, statusAccepted.http);
	}

}