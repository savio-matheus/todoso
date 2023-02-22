package todoso.arquivo.servico;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface ArquivoServico {

	void iniciar() throws Exception;

	void salvarArquivo(MultipartFile file, long idTarefa) throws Exception;

	Stream<Path> carregarTodos() throws Exception;

	Path carregar(String filename) throws Exception;

	Resource carregarResource(String filename) throws Exception;

	void apagarTudo();
	
	void deletar(String filename) throws Exception;

}