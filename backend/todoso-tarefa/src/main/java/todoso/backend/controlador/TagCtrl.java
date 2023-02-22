package todoso.backend.controlador;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import todoso.backend.dados.TagDTO;
import todoso.backend.dados.TarefaDTO;

@Hidden
@RestController
public class TagCtrl {

	@RequestMapping(
		value="/api/v1/tags",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Resposta<TagDTO>> getTag() {
		return getTag(null);
	}

	@RequestMapping(
		value="/api/v1/tags/{id}",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Resposta<TagDTO>> getTag(
		@PathVariable("id") Long id) {

		return null;
	}

	@RequestMapping(
		value="/api/v1/tags/{id}/tasks",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Resposta<TarefaDTO>> getTasksByTag(
		@PathVariable("id") Long id) {


		return null;
	}

	@RequestMapping(
		value="/api/v1/tags/{id}",
		method=RequestMethod.PATCH,
		consumes={MediaType.APPLICATION_JSON_VALUE},
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Resposta<TagDTO>> patchTag(
		@PathVariable("id") Long id, @Valid TagDTO tagNova) {
		return null;
	}
}
