package todoso.arquivo.dados;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author savio
 */
public class ArquivoDTO {
	
	@Positive
	@JsonProperty("id")
	private Long id;

	@JsonProperty("url")
	private String url;

	@NotEmpty(message="Mime type should not be empty")
	@JsonProperty("mime")
	private String mimetype;

	@JsonProperty("size")
	private Long tamanho;

	private long idRelacionadoTarefa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}

	public long getIdRelacionadoTarefa() {
		return idRelacionadoTarefa;
	}

	public void setIdRelacionadoTarefa(long idRelacionadoTarefa) {
		this.idRelacionadoTarefa = idRelacionadoTarefa;
	}

}
