package todoso.backend.controlador;

import todoso.backend.dados.ArquivoDAO;
import todoso.backend.dados.ArquivoDTO;

import javax.validation.Valid;
import java.util.ArrayList;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
public class ArquivoCtrl {

	//{"/api/v1/task/{taskId}/files/{fileId}",
	//"/api/v1/users/{userId}/files/{fileId}"}
	@RequestMapping(
		value="/api/v1/files/{fileId}",
		method=RequestMethod.GET
	)
	public ResponseEntity<Resource> getArquivo(
			@PathVariable("fileId") Long fileId) throws Exception {

		Resposta<ArquivoDTO> retorno = new Resposta<>();
		ArquivoDTO filtros = new ArquivoDTO();
		ArrayList<ArquivoDTO> lista = new ArrayList<>();
		ArquivoDAO arqDAO = new ArquivoDAO();

		if (fileId == null) {
			fileId = 0L;
		}
		filtros.setId(fileId);
		// TODO: criar classe ArquivoServico
		lista = (ArrayList<ArquivoDTO>) arqDAO.selecionar(filtros);

		retorno.setHttp(HttpStatus.OK);
		retorno.setDadosRetorno(lista);

		if (lista.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		InputStreamResource isr = new InputStreamResource(
			lista.get(0).getMultipartFile().getInputStream());

		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + lista.get(0).getNome() + "\"")
			.contentLength(lista.get(0).getTamanho())
			.contentType(MediaType.APPLICATION_OCTET_STREAM)
			.body(isr);
	}

	@RequestMapping(
		value={
			"/api/v1/files"
			//"/api/v1/tasks/{taskId}/files",
			//"/api/v1/users/{userId}/files"
		},
		method=RequestMethod.POST,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Resposta<ArquivoDTO>> postArquivo(
			@RequestParam("arquivo") MultipartFile file) throws Exception {

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
