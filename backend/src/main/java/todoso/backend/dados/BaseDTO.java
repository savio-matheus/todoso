package todoso.backend.dados;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	/**
	 * @return the limit
	 */
	public Long getLimite() {
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
	
	
}
