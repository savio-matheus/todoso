package todoso.backend.controlador;

import java.util.ArrayList;

import javax.validation.Valid;

import todoso.backend.dados.TarefaDTO;
import todoso.backend.servico.TarefaServico;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@ApiResponse(responseCode = "500", description = "Erro no servidor")
class TarefaCtrl {

	@Operation(summary = "Obtém uma lista de tarefas cadastradas.")
	@ApiResponse(responseCode = "200",
		description = "A busca foi realizada com sucesso, tendo ou não retornado resultados.")
	@RequestMapping(
			value = "/api/v1/tasks",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@CrossOrigin
	public ResponseEntity<Resposta<TarefaDTO>> getTarefa() throws Exception {
		return getTarefa(null);
	}

	@Operation(summary = "Obtém uma tarefa por meio de seu ID.")
	@ApiResponse(responseCode = "200",
		description = "A busca foi realizada com sucesso, tendo ou não retornado resultados.")
	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@CrossOrigin
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

	@Operation(summary = "Cria uma nova tarefa.")
	@ApiResponse(responseCode = "201", description = "A tarefa foi criada.")
	@ApiResponse(responseCode = "400",
		description = "Conteúdo mal formado, verifique as mensagens de retorno.")
	@RequestMapping(
			value = "/api/v1/tasks",
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@CrossOrigin
	public ResponseEntity<Resposta<TarefaDTO>> postTarefas(
			@RequestBody @Valid TarefaDTO tarefa) throws Exception {
		Resposta<TarefaDTO> retorno = new Resposta<>();
		TarefaServico servico = new TarefaServico();

		long id = servico.criarTarefa(tarefa).getId();

		retorno.setHttp(HttpStatus.CREATED);
		retorno.setId(id);

		return new ResponseEntity<>(retorno, HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Edita a tarefa que possui o ID informado.")
	@ApiResponse(responseCode = "200", description = "A tarefa foi editada.")
	@ApiResponse(responseCode = "400",
		description = "Conteúdo mal formado, verifique as mensagens de retorno.")
	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = {RequestMethod.PATCH},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE}
	)
	@CrossOrigin
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

	@Operation(summary = "Deleta a tarefa com o ID informado."+
			"Apague todos os anexos antes de deletar uma tarefa.")
	@ApiResponse(responseCode = "202",
		description =
			"A requisição será ou já foi atendida."+
			"Se a tarefa não existe, não faz nada.")
	@RequestMapping(
			value = "/api/v1/tasks/{id}",
			method = {RequestMethod.DELETE},
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	@CrossOrigin
	public ResponseEntity<Resposta<TarefaDTO>> deleteTarefas(
			@PathVariable("id") Long id) throws Exception {
		Resposta<TarefaDTO> retorno = new Resposta<>();
		TarefaDTO filtros = new TarefaDTO();
		TarefaServico servico = new TarefaServico();

		filtros.setId(id);

		long id_deletado = servico.deletarTarefa(filtros).getId();

		retorno.setHttp(HttpStatus.ACCEPTED);
		retorno.setId(id_deletado);

		return new ResponseEntity<>(retorno, HttpStatus.ACCEPTED);
	}

}
