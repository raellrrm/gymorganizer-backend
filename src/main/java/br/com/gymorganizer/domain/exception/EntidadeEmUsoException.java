package br.com.gymorganizer.domain.exception;

public class EntidadeEmUsoException extends RuntimeException {
    public EntidadeEmUsoException(String message) {
        super(message);
    }
}
