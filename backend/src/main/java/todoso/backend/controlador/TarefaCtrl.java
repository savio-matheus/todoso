package todoso.backend.controlador;

import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.Valid;

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
	private static final Status statusOk = Status.novo(HttpStatus.OK, "");
	private static final Status statusCreated = Status.novo(HttpStatus.CREATED, "");
	private static final Status statusAccepted = Status.novo(HttpStatus.ACCEPTED, "");

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
		HashMap<String, Object> retorno = new HashMap<>();
		TarefaDTO filtros = new TarefaDTO();
		ArrayList<TarefaDTO> lista = new ArrayList<>();
		TarefaServico servico = new TarefaServico();

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
	public ResponseEntity<Object> postTarefas(
			@RequestBody @Valid TarefaDTO tarefa) throws Exception {
		HashMap<String, Object> retorno = new HashMap<>();
		TarefaServico servico = new TarefaServico();

		long id = servico.criarTarefa(tarefa).getId();

		retorno.put("status", statusCreated);
		retorno.put("id", id);

		return new ResponseEntity<>(retorno, HttpStatus.ACCEPTED);
	}

	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = {RequestMethod.PATCH},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Object> patchTarefas(
			@PathVariable("id") Long id,
			@RequestBody @Valid TarefaDTO tarefa) throws Exception {
		HashMap<String, Object> retorno = new HashMap<>();
		TarefaServico servico = new TarefaServico();

		tarefa.setId(id);

		servico.editarTarefa(tarefa);
		retorno.put("status", statusOk);

		return new ResponseEntity<>(retorno, statusOk.http);
	}

	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = {RequestMethod.DELETE},
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Object> deleteTarefas(
			@PathVariable("id") Long id) throws Exception {
		HashMap<String, Object> retorno = new HashMap<>();
		TarefaDTO filtros = new TarefaDTO();
		TarefaServico servico = new TarefaServico();

		filtros.setId(id);

		servico.deletarTarefa(filtros);
		retorno.put("status", statusAccepted);
		retorno.put("id", id);

		return new ResponseEntity<>(retorno, statusAccepted.http);
	}

}