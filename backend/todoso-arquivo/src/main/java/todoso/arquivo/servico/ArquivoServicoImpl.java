package todoso.arquivo.servico;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArquivoServicoImpl implements ArquivoServico {

	private final Path raizCaminho;
	private final String raizCaminhoStr;

	@Autowired
	public ArquivoServicoImpl(ArquivoPropriedades props) {
		this.raizCaminho = Paths.get(props.getCaminho());
		this.raizCaminhoStr = props.getCaminho();
	}

	@Override
	public void salvarArquivo(MultipartFile arquivo, long idTarefa) throws Exception {
		String nomeArquivo = idTarefa + "_" + arquivo.getOriginalFilename();
		try {
			if (arquivo.isEmpty()) {
				throw new Exception("Failed to store empty file.");
			}
			Path arquivoEmDisco = this.raizCaminho.resolve(Paths.get(nomeArquivo))
					.normalize().toAbsolutePath();
			if (!arquivoEmDisco.getParent().equals(this.raizCaminho.toAbsolutePath())) {
				throw new Exception(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = arquivo.getInputStream()) {
				Files.copy(inputStream, arquivoEmDisco,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new Exception("Failed to store file.", e);
		}
	}

	@Override
	public Stream<Path> carregarTodos() throws Exception {
		try {
			return Files.walk(this.raizCaminho, 1)
				.filter(path -> !path.equals(this.raizCaminho))
				.map(this.raizCaminho::relativize);
		}
		catch (IOException e) {
			throw new Exception("Failed to read stored files", e);
		}

	}

	@Override
	public Path carregar(String filename) {
		return raizCaminho.resolve(filename);
	}

	@Override
	public Resource carregarResource(String filename) throws Exception {
		try {
			Path file = carregar(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new Exception(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new Exception("Could not read file: " + filename, e);
		}
	}

	@Override
	public void apagarTudo() {
		FileSystemUtils.deleteRecursively(raizCaminho.toFile());
	}
	
	@Override
	public void deletar(String filename) throws Exception {
		Files.deleteIfExists(Path.of(raizCaminhoStr + "/" + filename));
	}

	@Override
	public void iniciar() throws Exception {
		try {
			Files.createDirectories(raizCaminho);
		}
		catch (IOException e) {
			throw new Exception("Could not initialize storage", e);
		}
	}
}
