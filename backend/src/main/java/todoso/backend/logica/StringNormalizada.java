package todoso.backend.logica;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * String cujos caracteres permitidos são reduzidos a letras, números, e
 * alguns símbolos (somente ASCII). É útil para representar URLs, nomes de
 * arquivos, entre outros.
 **/
public class StringNormalizada {
	final private String original;

	public StringNormalizada(String s) {
		this.original = s;
	}

	/** String em formato adequado para URLs (application/x-www-form-urlencoded).
	 * O charset padrão é UTF-8. */
	public String paraURL() {
		return URLEncoder.encode(this.original, StandardCharsets.UTF_8);
	}

	/** String em formato adequado para URLs (application/x-www-form-urlencoded).
	 * @param charset uma das constantes definidas em StandardCharsets */
	public String paraURL(Charset charset) {
		return URLEncoder.encode(this.original, charset);
	}

	/** String em formato aceitável por todos os sistemas operacionais. */
	public String nomeDeArquivo() {
		String s = this.removePontosDiretorio(this.original)
			.replaceAll("[\\\\/:*?\"<>|]", "");
		return s;
	}

	/**
	 * String com os espaços (inclui tabulação e quebra de linha) substituídos
	 * pelo caractereAlternativo. */
	public String semEspacos(String caractereAlternativo) {
		return original.replaceAll("\\s", caractereAlternativo);
	}

	public String original() {
		return this.original;
	}

	/**
	 * O mesmo que nomeDeArquivo(). */
	@Override
	public String toString() {
		return this.nomeDeArquivo();
	}

	/** Se a string de entrada for "." ou "..", substitui cada ponto
	 * pela string "ponto". */
	private String removePontosDiretorio(String s) {
		if (".".equals(s) || "..".equals(s))
			return s.replaceAll("[.]", "ponto");
		else
			return s;
	}
}
