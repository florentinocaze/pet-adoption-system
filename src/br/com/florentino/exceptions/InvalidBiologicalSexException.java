package br.com.florentino.exceptions;

public class InvalidBiologicalSexException extends RuntimeException {
    public InvalidBiologicalSexException(String message) {
        super(message);
    }

    public InvalidBiologicalSexException(String message, Throwable cause) {
        super(message, cause);
    }
}
