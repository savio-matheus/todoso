package todoso.arquivo.servico;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class ArquivoPropriedades {

	/**
	 * Folder location for storing files
	 */
	private String caminho = "../todoso-tarefas-anexos";

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

}
