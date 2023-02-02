package todoso.backend.controlador;

import todoso.backend.dados.CategoriaDTO;
import todoso.backend.servico.CategoriaServico;

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

	@RequestMapping(
		value="/api/v1/categories",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Resposta<CategoriaDTO>> getCategoria() throws Exception {
		return getCategoria(null);
	}

	@RequestMapping(
		value="/api/v1/categories/{id}",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
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

	@RequestMapping(
		value="/api/v1/categories",
		method=RequestMethod.POST,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Resposta<CategoriaDTO>> postCategoria(
			@RequestBody @Valid CategoriaDTO categoria) throws Exception {

		Resposta<CategoriaDTO> retorno = new Resposta<>();
		CategoriaServico servico = new CategoriaServico();

		long retId = servico.criarCategoria(categoria).getId();

		retorno.setHttp(HttpStatus.CREATED);
		retorno.setId(retId);

		return new ResponseEntity<>(retorno, HttpStatus.CREATED);
	}

	@RequestMapping(
		value="/api/v1/categories/{id}",
		method=RequestMethod.PATCH,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
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

	@RequestMapping(
		value="/api/v1/categories/{id}",
		method=RequestMethod.DELETE,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
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
