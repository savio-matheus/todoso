package todoso.backend.dados;

import java.io.File;
import java.nio.file.Files;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileImpl implements MultipartFile {

	private final File file;

	public MultipartFileImpl(File file) {
		this.file = file;
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public String getOriginalFilename() {
		return file.getName();
	}

	@Override
	public String getContentType() {
		try {
			return Files.probeContentType(file.toPath());
		} catch (IOException e) {
			throw new RuntimeException("Error while extracting MIME type of file", e);
		}
	}

	@Override
	public boolean isEmpty() {
		return file.length() == 0;
	}

	@Override
	public long getSize() {
		return file.length();
	}

	@Override
	public byte[] getBytes() throws IOException {
		return Files.readAllBytes(file.toPath());
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		// TODO: será que isso funciona com tamanhos arbitrários? Acho que não...
		Files.write(
			dest.toPath(),
			Files.readAllBytes(dest.toPath())
		);
	}
}