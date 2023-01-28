package todoso.backend.controlador;

import todoso.backend.dados.CategoriaDTO;
import todoso.backend.servico.CategoriaServico;

import java.util.HashMap;

import javax.validation.Valid;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CategoriaCtrl {
	private static final Status statusOk = Status.novo(HttpStatus.OK, "");
	private static final Status statusCreated = Status.novo(HttpStatus.CREATED, "");
	private static final Status statusAccepted = Status.novo(HttpStatus.ACCEPTED, "");

	@RequestMapping(
		value="/api/v1/categories",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> getCategoria() throws Exception {
		return getCategoria(null);
	}

	@RequestMapping(
		value="/api/v1/categories/{id}",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> getCategoria(
		@PathVariable("id") Long id) throws Exception {

		HashMap<String, Object> retorno = new HashMap<>();
		CategoriaDTO filtros = new CategoriaDTO();
		ArrayList<CategoriaDTO> lista = new ArrayList<>();
		CategoriaServico servico = new CategoriaServico();

		if (id != null) {
			filtros.setId(id);
		}

		lista = servico.selecionarCategorias(filtros);

		retorno.put("status", statusOk);
		retorno.put("data", lista);

		return new ResponseEntity<>(retorno, statusOk.http);
	}

	@RequestMapping(
		value="/api/v1/categories",
		method=RequestMethod.POST,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> postCategoria(
			@RequestBody @Valid CategoriaDTO categoria) throws Exception {

		HashMap<String, Object> retorno = new HashMap<>();
		CategoriaServico servico = new CategoriaServico();

		long retId = servico.criarCategoria(categoria).getId();
		retorno.put("status", statusCreated);
		retorno.put("id", retId);

		return new ResponseEntity<>(retorno, statusCreated.http);
	}

	@RequestMapping(
		value="/api/v1/categories/{id}",
		method=RequestMethod.PATCH,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> patchCategoria(
			@PathVariable("id") Long id,
			@RequestBody @Valid CategoriaDTO categoria) throws Exception {

		HashMap<String, Object> retorno = new HashMap<>();
		CategoriaServico servico = new CategoriaServico();

		categoria.setId(id);
		long retId = servico.editarCategoria(categoria).getId();

		retorno.put("status", statusOk);
		retorno.put("id", retId);

		return new ResponseEntity<>(retorno, statusOk.http);
	}

	@RequestMapping(
		value="/api/v1/categories/{id}",
		method=RequestMethod.DELETE,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> deleteCategoria(
		@PathVariable("id") Long id) throws Exception {

		HashMap<String, Object> retorno = new HashMap<>();
		CategoriaDTO filtros = new CategoriaDTO();
		CategoriaServico servico = new CategoriaServico();

		filtros.setId(id);

		long retId = servico.deletarCategoria(filtros).getId();

		retorno.put("status", statusAccepted);
		retorno.put("id", retId);

		return new ResponseEntity<>(retorno, statusAccepted.http);
	}
}