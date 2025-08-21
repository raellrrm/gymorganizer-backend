package br.com.gymorganizer.domain.exception;

public class PlanoNaoEncontrado extends EntidadeNaoEncontradaException {
    public PlanoNaoEncontrado(String message) {
        super(message);
    }

    public PlanoNaoEncontrado(Long planoId) {
        super(String.format("Plano id: %d não foi encontrado", planoId));
    }
}
