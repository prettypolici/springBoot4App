package se.magnus.api.exceptions;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {}

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public InvalidInputException(Throwable throwable) {
        super(throwable);
    }
}
