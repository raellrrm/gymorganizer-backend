package br.com.gymorganizer.domain.exception;

public class PlanoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public PlanoNaoEncontradoException(String message) {
        super(message);
    }

    public PlanoNaoEncontradoException(Long PlanoId) {
        this(String.format("Plano id: %d não encontrado", PlanoId));
    }
}
