package br.com.gymorganizer.domain.exception;

public class CpfNaoEncontradoException extends EntidadeNaoEncontradaException {
    public CpfNaoEncontradoException(String cpf) {
        super(String.format("Cpf: %s não foi encontrado", cpf));
    }
}
