package todoso.backend.controlador;

import todoso.backend.dados.ArquivoDTO;
import todoso.backend.servico.ArquivoServico;
//import todoso.backend.servico.ConfiguracoesServico;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.catalina.connector.Response;
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
import org.springframework.web.multipart.MultipartFile;

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
		ArquivoServico servico = new ArquivoServico();

		if (fileId == null) {
			fileId = 0L;
		}
		filtros.setId(fileId);
		// TODO: criar classe ArquivoServico
		lista = (ArrayList<ArquivoDTO>) servico.selecionarArquivos(filtros);

		retorno.setHttp(HttpStatus.OK);
		retorno.setDadosRetorno(lista);

		if (lista.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// TODO: isso tá bem ruim. Criar solução para n arquivos
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

		ArquivoServico servico = new ArquivoServico();
		ArquivoDTO arquivo = new ArquivoDTO();
		arquivo.setMultipartFile(file);

		arquivo = servico.criarArquivo(arquivo);
		long idGerado = arquivo.getId();

		Resposta<ArquivoDTO> r = new Resposta<>();
		r.setId(idGerado);
		r.setHttp(HttpStatus.ACCEPTED);

		return new ResponseEntity<>(r, HttpStatus.OK);
	}

	@RequestMapping(
		value={
			"/api/v1/files/{fileId}"
			//"/api/v1/task/{id}/files/{fileId}",
			//"/api/v1/users/{id}/files/{fileId}"
		},
		method=RequestMethod.DELETE,
		produces={MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<Resposta<ArquivoDTO>> deleteArquivo(
			@PathVariable Long fileId) throws Exception {

		//ConfiguracoesServico config = new ConfiguracoesServico();

		Resposta<ArquivoDTO> retorno = new Resposta<>();
		ArquivoServico servico = new ArquivoServico();
		ArquivoDTO filtros = new ArquivoDTO();
		filtros.setId(fileId);

		long retId = servico.deletarArquivo(filtros).getId();

		retorno.setHttp(HttpStatus.ACCEPTED);
		retorno.setId(retId);

		return new ResponseEntity<>(retorno, HttpStatus.ACCEPTED);
	}

	private ResponseEntity<Resposta<ArquivoDTO>> adicionarArquivo(
		MultipartFile file, Optional<Long> idUsuario, Optional<Long> idTarefa) throws Exception {
		
		if (idUsuario.isEmpty() && idTarefa.isEmpty()) {
			throw new Exception("O arquivo deve ser vinculado a um usuário ou tarefa."); 
		}
		
		return null;
	}

	private ResponseEntity<Resource> baixarArquivo(
		Long idArquivo, Optional<Long> idUsuario, Optional<Long> idTarefa) throws Exception {

		if (idArquivo < 0 || null == idArquivo) {
			throw new Exception("Id inválido para o arquivo.");
		}
		if (idUsuario.isEmpty() && idTarefa.isEmpty()) {
			throw new Exception("Informe a qual tarefa ou usuário o arquivo está associado.");
		}

		return null;
	}

	private ResponseEntity<Resposta<ArquivoDTO>> deletarArquivo(
		Long idArquivo, Optional<Long> idUsuario, Optional<Long> idTarefa) throws Exception {

		if (idArquivo < 0 || null == idArquivo) {
			throw new Exception("Id inválido para o arquivo.");
		}
		if (idUsuario.isEmpty() && idTarefa.isEmpty()) {
			throw new Exception("Informe a qual tarefa ou usuário o arquivo está associado.");
		}

		return null;
	}
}

