package br.com.florentino.exceptions;

public class InvalidWeightException extends RuntimeException {
    public InvalidWeightException(String message) {
        super(message);
    }

    public InvalidWeightException(String message, Throwable cause) {
        super(message, cause);
    }
}
