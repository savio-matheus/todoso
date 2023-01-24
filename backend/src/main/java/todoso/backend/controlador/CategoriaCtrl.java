package todoso.backend.controlador;

import todoso.backend.dados.CategoriaDTO;
import todoso.backend.servico.CategoriaServico;

import java.util.HashMap;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CategoriaCtrl {

	private HashMap<String, Object> retorno = new HashMap<>();
	private CategoriaDTO filtros = new CategoriaDTO();
	private ArrayList<CategoriaDTO> lista = new ArrayList<>();
	private ObjectMapper conversor = new ObjectMapper();

	private CategoriaDTO catDTO = null;
	private CategoriaServico servico = new CategoriaServico();

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

		if (id != null) {
			filtros.setId(id);
		}

		lista = servico.selecionarCategorias(filtros);
		//System.out.println(lista.size());

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
		@RequestBody String json) throws Exception {

		catDTO = conversor.readValue(json, CategoriaDTO.class);
		long retId = servico.criarCategoria(catDTO).getId();
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
		@PathVariable("id") Long id, @RequestBody String json) throws Exception {

		catDTO = conversor.readValue(json, CategoriaDTO.class);
		catDTO.setId(id);
		long retId = servico.editarCategoria(catDTO).getId();

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

		filtros.setId(id);

		long retId = servico.deletarCategoria(filtros).getId();

		retorno.put("status", statusAccepted);
		retorno.put("id", retId);

		return new ResponseEntity<>(retorno, statusAccepted.http);
	}
}