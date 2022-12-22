package todoso.backend.controlador;

import todoso.backend.dados.CategoriaDTO;
import todoso.backend.dados.Categorias;

import java.util.HashMap;
import java.util.ArrayList;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CategoriaAPIv1 {

	private HashMap<String, Object> retorno = new HashMap<>();
	private Categorias dados = new Categorias();
	private CategoriaDTO filtros = new CategoriaDTO();
	private ArrayList<CategoriaDTO> lista = new ArrayList<>();
	private ObjectMapper conversor = new ObjectMapper();

	private CategoriaDTO catDTO = null;

	@RequestMapping(
		value="/api/v1/categories/{id}",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> getCategoria(
		@PathVariable("id") Long id) {

		if (id != null) {
			filtros.setId(id);
		}
		
		try {
			lista = dados.selecionar(filtros);
			System.out.println(lista.size());
		} catch (SQLException e) {
			e.printStackTrace();
			retorno.put(
				"status",
				Status.novo(500, "INTERNAL SERVER ERROR")
			);
			return new ResponseEntity<>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (lista == null || lista.size() <= 0) {
			retorno.put(
				"status",
				Status.novo(404, "Not Found")
			);
			return new ResponseEntity<>(retorno, HttpStatus.NOT_FOUND);
		}

		retorno.put(
			"status",
			Status.novo(200, "OK")
		);
		retorno.put("data", lista);

		return new ResponseEntity<>(retorno, HttpStatus.OK);
	}

	@RequestMapping(
		value="/api/v1/categories",
		method=RequestMethod.POST,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> postCategoria(
		@RequestBody String json
	) {

		try {
			lista = conversor.readValue(json,
				new TypeReference<ArrayList<CategoriaDTO>>(){}
			);
			dados.inserir(lista);
			retorno.put(
				"status",
				Status.novo(200, "OK")
			);
			//retorno.put("data", lista);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
			retorno.put(
			"status",
				Status.novo(400, "BAD REQUEST")
			);
			new ResponseEntity<>(retorno, HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			e.printStackTrace();
			retorno.put(
			"status",
				Status.novo(500, "INTERNAL SERVER ERROR")
			);
			new ResponseEntity<>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(retorno, HttpStatus.OK);
	}

	@RequestMapping(
		value="/api/v1/categories/{id}",
		method=RequestMethod.PATCH,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> patchCategoria(
		@PathVariable("id") Long id, @RequestBody String json
	) {

		if (id == null) {
			retorno.put(
			"status",
				Status.novo(405, "METHOD NOT ALLOWED - no id")
			);
			return new ResponseEntity<>(retorno, HttpStatus.METHOD_NOT_ALLOWED);
		}

		try {
			catDTO = conversor.readValue(json, CategoriaDTO.class);
			catDTO.setId(id);
			int atualizacoes = dados.atualizar(catDTO);
			Status status = new Status();
			
			status.codigo = 200;
			status.mensagem = "OK - Updated";

			if (atualizacoes <= 0) {
				status.codigo = 404;
				status.mensagem = "NOT FOUND - verify sent id";
			}

			retorno.put("status", status);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
			retorno.put("status", Status.novo(400, "BAD REQUEST"));
			new ResponseEntity<>(retorno, HttpStatus.BAD_REQUEST);
		}
		catch (SQLException e) {
			e.printStackTrace();
			retorno.put("status", Status.novo(500, "INTERNAL SERVER ERROR"));
			new ResponseEntity<>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(retorno, HttpStatus.OK);
	}

	@RequestMapping(
		value="/api/v1/categories/{id}",
		method=RequestMethod.DELETE,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> deleteCategoria(
		@PathVariable("id") Long id
	) {

		if (id == null) {
			retorno.put(
			"status",
				Status.novo(
					405,
					"METHOD NOT ALLOWED - no id found"
				)
			);
			return new ResponseEntity<>(retorno, HttpStatus.METHOD_NOT_ALLOWED);
		}

		filtros.setId(id);

		try {
			int atualizacoes = dados.excluir(filtros);
			Status status = new Status();
			status.codigo = 200;
			status.mensagem = "OK";

			if (atualizacoes <= 0) {
				status.codigo = 404;
				status.mensagem = "NOT FOUND - id: " + id;
			}

			retorno.put("status", status);
		}
		catch (SQLException e) {
			e.printStackTrace();
			retorno.put("status", "{'code':500, 'message':'Internal Server Error'}");
			new ResponseEntity<>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return null;
	}
}