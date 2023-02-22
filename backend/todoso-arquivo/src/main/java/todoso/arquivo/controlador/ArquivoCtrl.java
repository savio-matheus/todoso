package todoso.arquivo.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import todoso.arquivo.dados.ArquivoDAO;
import todoso.arquivo.servico.ArquivoServico;

@RestController
public class ArquivoCtrl {
	
	private final ArquivoServico arquivoServico;
	
	@Autowired
	public ArquivoCtrl(ArquivoServico arquivoServico) {
		this.arquivoServico = arquivoServico;
	}
	
	@RequestMapping(
		value="/api/v1/files/{filename:.+}",
		method=RequestMethod.GET,
		produces={MediaType.MULTIPART_FORM_DATA_VALUE}
	)
	public ResponseEntity<Object> getArquivo(@PathVariable String filename) throws Exception {
		Resource arquivo = arquivoServico.carregarResource(filename);

		return ResponseEntity
			.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + arquivo.getFilename() + "\"")
			.body(arquivo);
	}

	@RequestMapping(
		value="/api/v1/files/task/{id}",
		method=RequestMethod.POST,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Object> postArquivo(
			@PathVariable Long id,
			@RequestParam("arquivo") MultipartFile file) throws Exception {
		
		String nome = new ArquivoDAO().inserir(file, id);
		arquivoServico.salvarArquivo(file, id);
		
		return new ResponseEntity<Object>("{'nome': '"+nome+"'}", HttpStatus.CREATED);
	}

	@RequestMapping(
		value="/api/v1/files/{filename:.+}",
		method=RequestMethod.DELETE,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Object> deleteArquivo(@PathVariable String filename) throws Exception {
		ArquivoDAO arqDAO = new ArquivoDAO();
		long id = arqDAO.buscarIdPorNome(filename);
		
		if (id <= 0) {
			throw new Exception("File not found.");
		}
		
		arqDAO.deletar(id);
		arquivoServico.deletar(filename);

		return new ResponseEntity<Object>("{'id': '"+id+"'}", HttpStatus.ACCEPTED);
	}
	
	// Exceção genérica
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception exc) {
		exc.printStackTrace();
		System.out.println(exc.getCause());
		return new ResponseEntity(exc.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
