package todoso.backend.dados;

import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author savio
 */
public abstract class BaseDTO {
	@JsonIgnore
	private Long limite = null;
	
	@JsonIgnore
	private Long offset = null;
	
	@JsonIgnore
	private Long pagina = null;

	// Quando um DTO deve ser relacionado o outro, utiliza-se este id para
	// representa-lo.
	@JsonIgnore
	private Long idRelacionadoCategoria = null;

	@JsonIgnore
	private Long idRelacionadoTag = null;

	@JsonIgnore
	private Long idRelacionadoUsuario = null;

	@JsonIgnore
	private Long idRelacionadoArquivo = null;

	/**
	 * @return the limit
	 */
	public Long getLimite() {
		if (limite == null) {
			return Long.MAX_VALUE;
		}
		return limite;
	}

	/**
	 * @param limite the limit to set
	 */
	public void setLimite(Long limite) {
		this.limite = limite;
	}

	/**
	 * @return the offset
	 */
	public Long getOffset() {
		if (offset == null) {
			return Long.valueOf(0);
		}
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Long offset) {
		this.offset = offset;
	}

	/**
	 * @return the page
	 */
	public Long getPagina() {
		return pagina;
	}

	/**
	 * @param pagina the page to set
	 */
	public void setPagina(Long pagina) {
		this.pagina = pagina;
	}
	
	/**
	 * */
	public String filtroLike(Object dado) {
		if (dado == null) {
			return "%";
		}
		return dado.toString();
	}

	public Long getIdRelacionadoCategoria() {
		return idRelacionadoCategoria;
	}

	public void setIdRelacionadoCategoria(Long idRelacionadoCategoria) {
		this.idRelacionadoCategoria = idRelacionadoCategoria;
	}

	public Long getIdRelacionadoTag() {
		return idRelacionadoTag;
	}

	public void setIdRelacionadoTag(Long idRelacionadoTag) {
		this.idRelacionadoTag = idRelacionadoTag;
	}

	public Long getIdRelacionadoUsuario() {
		return idRelacionadoUsuario;
	}

	public void setIdRelacionadoUsuario(Long idRelacionadoUsuario) {
		this.idRelacionadoUsuario = idRelacionadoUsuario;
	}

	public Long getIdRelacionadoArquivo() {
		return idRelacionadoArquivo;
	}

	public void setIdRelacionadoArquivo(Long idRelacionadoArquivo) {
		this.idRelacionadoArquivo = idRelacionadoArquivo;
	}
}
