package todoso.backend.controlador;

import todoso.backend.dados.ArquivoDAO;
import todoso.backend.dados.ArquivoDTO;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
class ArquivoCtrl {

	@RequestMapping(
		value={
			"/api/v1/task/{taskId}/files/{fileId}",
			"/api/v1/users/{userId}/files/{fileId}"
		},
		method=RequestMethod.GET,
		produces={MediaType.MULTIPART_FORM_DATA_VALUE}
	)
	public ResponseEntity<Resposta<ArquivoDTO>> getArquivo(
			@PathVariable("id") Long taskId,
			@PathVariable("id") Long fileId,
			@PathVariable("id") Long userId) throws Exception {


		return null;
	}

	@RequestMapping(
		value={
			"/api/v1/task/{taskId}/files"
		},
		method=RequestMethod.POST,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Resposta<ArquivoDTO>> postArquivo(
			@PathVariable Long taskId,
			@RequestParam("arquivo") MultipartFile file) throws Exception {

		System.out.println("taskId: " + taskId);

		ArquivoDTO arquivo = new ArquivoDTO();
		arquivo.setMultipartFile(file);
		
		ArquivoDAO arqDAO = new ArquivoDAO();
		long idGerado = arqDAO.inserir(arquivo);

		Resposta<ArquivoDTO> r = new Resposta<>();
		r.setId(idGerado);
		r.setHttp(HttpStatus.ACCEPTED);

		return new ResponseEntity<>(r, HttpStatus.OK);
	}

	@RequestMapping(
		value={
			"/api/v1/task/{taskId}/files/{fileId}",
			"/api/v1/users/{userId}/files/{fileId}"
		},
		method=RequestMethod.DELETE,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Resposta<ArquivoDTO>> deleteArquivo(
			@PathVariable Long fileId,
			@PathVariable Long taskId,
			@PathVariable Long userId) throws Exception {

		return null;
	}
}
