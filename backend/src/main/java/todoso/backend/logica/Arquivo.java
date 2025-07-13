package todoso.backend.logica;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.MimeType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Arquivo {
	private StringNormalizada nome;
	private MimeType mimetype;
	private Long tamanhoBytes;

	private String hash = "";

	private MultipartFile multipartFile;

	public Arquivo(
		StringNormalizada nome,
		MimeType mimetype,
		Long tamanhoBytes
	) {
		this.nome = nome;
		this.mimetype = mimetype;
		this.tamanhoBytes = tamanhoBytes;
	}

	public Arquivo(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
		this.tamanhoBytes = multipartFile.getSize();
		this.mimetype = new MimeType(multipartFile.getContentType());
		this.nome = new StringNormalizada(multipartFile.getOriginalFilename());
	}
}