package todoso.backend.dados;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author savio
 */
public class ArquivoDTO extends BaseDTO {
	
	@Positive
	@JsonProperty("id")
	private Long id;

	// TODO: validar nome do arquivo (ex.: barras, pontos etc.)
	@JsonProperty("fileName")
	private String nome;

	@NotEmpty(message="Mime type should not be empty")
	@JsonProperty("mime")
	private String mimetype;

	@JsonProperty("size")
	private Long tamanho;

	private MultipartFile multipartFile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public String getMimetype() {
		return mimetype;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public void setMultipartFile(@NotNull MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	
		this.tamanho = multipartFile.getSize();
		this.mimetype = multipartFile.getContentType();
		this.nome = multipartFile.getOriginalFilename();
	}

	public MultipartFile getMultipartFile() {
		return this.multipartFile;
	}
}
