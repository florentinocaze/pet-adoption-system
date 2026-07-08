package br.com.florentino.exceptions;

public class InvalidRaceException extends RuntimeException {
    public InvalidRaceException(String message) {
        super(message);
    }

    public InvalidRaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
