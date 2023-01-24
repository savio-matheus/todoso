package todoso.backend.controlador;

import java.util.HashMap;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import todoso.backend.dados.TagDTO;

@RestController
public class TagCtrl {

	@RequestMapping(
		value="/api/v1/tags",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> getTag() {
		return getTag(null);
	}

	@RequestMapping(
		value="/api/v1/tags/{id}",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> getTag(
		@PathVariable("id") Long id) {

		return null;
	}

	@RequestMapping(
		value="/api/v1/tags/{id}/tasks",
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> getTasksByTag(
		@PathVariable("id") Long id) {


		return null;
	}

	@RequestMapping(
		value="/api/v1/tags/{id}",
		method=RequestMethod.PATCH,
		consumes={MediaType.APPLICATION_JSON_VALUE},
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<HashMap<String, Object>> patchTag() {
		return null;
	}
}