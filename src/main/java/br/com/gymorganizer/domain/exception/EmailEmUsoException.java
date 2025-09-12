package br.com.gymorganizer.domain.exception;

public class EmailEmUsoException extends RuntimeException {
    public EmailEmUsoException(String message) {
        super(message);
    }
}
