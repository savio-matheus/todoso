package todoso.backend.controlador;

import todoso.backend.dados.CategoriaDTO;
import todoso.backend.dados.TarefaDTO;
import todoso.backend.servico.CategoriaServico;
import todoso.backend.servico.TarefaServico;

import javax.validation.Valid;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CategoriaCtrl {

	@Operation(summary = "Obtém uma lista de categorias cadastradas.")
	@ApiResponse(responseCode = "200",
		description = "A busca foi realizada com sucesso, tendo ou não retornado resultados.")
	@RequestMapping(
		value="/api/v1/categories",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	@CrossOrigin
	public ResponseEntity<Resposta<CategoriaDTO>> getCategoria() throws Exception {
		return getCategoria(null);
	}

	@Operation(summary = "Obtém uma categoria a partir de seu ID.")
	@ApiResponse(responseCode = "200",
		description = "A busca foi realizada com sucesso, tendo ou não retornado resultados.")
	@RequestMapping(
		value="/api/v1/categories/{id}",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	@CrossOrigin
	public ResponseEntity<Resposta<CategoriaDTO>> getCategoria(
		@PathVariable("id") Long id) throws Exception {

		Resposta<CategoriaDTO> retorno = new Resposta<>();
		CategoriaDTO filtros = new CategoriaDTO();
		ArrayList<CategoriaDTO> lista = new ArrayList<>();
		CategoriaServico servico = new CategoriaServico();

		if (id != null) {
			filtros.setId(id);
		}

		lista = servico.selecionarCategorias(filtros);

		retorno.setHttp(HttpStatus.OK);
		retorno.setDadosRetorno(lista);

		return new ResponseEntity<>(retorno, HttpStatus.OK);
	}

	@Operation(summary = "Obtém a lista de tarefas em determinada categoria.")
	@ApiResponse(responseCode = "200",
		description = "A busca foi realizada com sucesso, tendo ou não retornado resultados.")
	@RequestMapping(
		value="/api/v1/categories/{id}/tasks",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	@CrossOrigin
	public ResponseEntity<Resposta<TarefaDTO>> getTarefasPorCategoria(
		@PathVariable("id") Long id) throws Exception {

		Resposta<TarefaDTO> retorno = new Resposta<>();
		TarefaDTO filtros = new TarefaDTO();
		ArrayList<TarefaDTO> lista = new ArrayList<>();
		TarefaServico servico = new TarefaServico();

		if (id == null) {
			throw new IllegalArgumentException("Id must not be null.");
		}

		filtros.setIdRelacionadoCategoria(id);

		lista = servico.selecionarTarefas(filtros);

		retorno.setHttp(HttpStatus.OK);
		retorno.setDadosRetorno(lista);

		return new ResponseEntity<>(retorno, HttpStatus.OK);
	}

	@Operation(summary = "Cria uma nova categoria.")
	@ApiResponse(responseCode = "201", description = "A categoria foi criada.")
	@ApiResponse(responseCode = "400",
		description = "Conteúdo mal formado, verifique as mensagens de retorno.")
	@RequestMapping(
		value="/api/v1/categories",
		method=RequestMethod.POST,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	@CrossOrigin
	public ResponseEntity<Resposta<CategoriaDTO>> postCategoria(
			@RequestBody @Valid CategoriaDTO categoria) throws Exception {

		Resposta<CategoriaDTO> retorno = new Resposta<>();
		CategoriaServico servico = new CategoriaServico();

		long retId = servico.criarCategoria(categoria).getId();

		retorno.setHttp(HttpStatus.CREATED);
		retorno.setId(retId);

		return new ResponseEntity<>(retorno, HttpStatus.CREATED);
	}

	@Operation(summary = "Edita a categoria que possui o ID informado.")
	@ApiResponse(responseCode = "200", description = "A categoria foi editada.")
	@ApiResponse(responseCode = "400",
		description = "Conteúdo mal formado, verifique as mensagens de retorno.")
	@RequestMapping(
		value="/api/v1/categories/{id}",
		method=RequestMethod.PATCH,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	@CrossOrigin
	public ResponseEntity<Resposta<CategoriaDTO>> patchCategoria(
			@PathVariable("id") Long id,
			@RequestBody @Valid CategoriaDTO categoria) throws Exception {

		Resposta<CategoriaDTO> retorno = new Resposta<>();
		CategoriaServico servico = new CategoriaServico();

		categoria.setId(id);
		long retId = servico.editarCategoria(categoria).getId();

		retorno.setHttp(HttpStatus.OK);
		retorno.setId(retId);

		return new ResponseEntity<>(retorno, HttpStatus.OK);
	}

	@Operation(summary = "Deleta a categoria com o ID informado")
	@ApiResponse(responseCode = "202",
		description =
			"A requisição será ou já foi atendida."+
			"Se a categoria não existe, não faz nada.")
	@RequestMapping(
		value="/api/v1/categories/{id}",
		method=RequestMethod.DELETE,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	@CrossOrigin
	public ResponseEntity<Resposta<CategoriaDTO>> deleteCategoria(
		@PathVariable("id") Long id) throws Exception {

		Resposta<CategoriaDTO> retorno = new Resposta<>();
		CategoriaDTO filtros = new CategoriaDTO();
		CategoriaServico servico = new CategoriaServico();

		filtros.setId(id);

		long retId = servico.deletarCategoria(filtros).getId();

		retorno.setHttp(HttpStatus.ACCEPTED);
		retorno.setId(retId);

		return new ResponseEntity<>(retorno, HttpStatus.ACCEPTED);
	}
}
