package todoso.backend.controlador;

import java.util.ArrayList;

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

	@RequestMapping(
			value = "/api/v1/tasks",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Resposta<TarefaDTO>> getTarefa() throws Exception {
		return getTarefa(null);
	}

	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Resposta<TarefaDTO>> getTarefa(@PathVariable("id") Long id) throws Exception {
		Resposta<TarefaDTO> retorno = new Resposta<>();
		TarefaDTO filtros = new TarefaDTO();
		ArrayList<TarefaDTO> lista = new ArrayList<>();
		TarefaServico servico = new TarefaServico();

		filtros.setId(id);
		lista = servico.selecionarTarefas(filtros);

		retorno.setHttp(HttpStatus.OK);
		retorno.setDadosRetorno(lista);

		return new ResponseEntity<>(retorno, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/api/v1/tasks",
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Resposta<TarefaDTO>> postTarefas(
			@RequestBody @Valid TarefaDTO tarefa) throws Exception {
		Resposta<TarefaDTO> retorno = new Resposta<>();
		TarefaServico servico = new TarefaServico();

		long id = servico.criarTarefa(tarefa).getId();

		retorno.setHttp(HttpStatus.CREATED);
		retorno.setId(id);

		return new ResponseEntity<>(retorno, HttpStatus.ACCEPTED);
	}

	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = {RequestMethod.PATCH},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Resposta<TarefaDTO>> patchTarefas(
			@PathVariable("id") Long id,
			@RequestBody @Valid TarefaDTO tarefa) throws Exception {
		Resposta<TarefaDTO> retorno = new Resposta<>();
		TarefaServico servico = new TarefaServico();

		tarefa.setId(id);

		servico.editarTarefa(tarefa);
		retorno.setHttp(HttpStatus.OK);

		return new ResponseEntity<>(retorno, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = {RequestMethod.DELETE},
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Resposta<TarefaDTO>> deleteTarefas(
			@PathVariable("id") Long id) throws Exception {
		Resposta<TarefaDTO> retorno = new Resposta<>();
		TarefaDTO filtros = new TarefaDTO();
		TarefaServico servico = new TarefaServico();

		filtros.setId(id);

		servico.deletarTarefa(filtros);

		retorno.setHttp(HttpStatus.ACCEPTED);
		retorno.setId(id);

		return new ResponseEntity<>(retorno, HttpStatus.ACCEPTED);
	}

}
