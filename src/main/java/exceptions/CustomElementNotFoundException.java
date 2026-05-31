package exceptions;

public class CustomElementNotFoundException extends RuntimeException {

    public CustomElementNotFoundException(String message) {
        super(message);
    }

    public CustomElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
