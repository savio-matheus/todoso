package todoso.backend.excecoes;

public class InvalidRequestException extends Exception {
	public InvalidRequestException(String message) {
		super(message);
	}

	public InvalidRequestException() {
		super();
	}
}
