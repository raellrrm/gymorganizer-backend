package br.com.gymorganizer.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(Long usuarioId) {
        super(String.format("Usuário id: %d não foi encontrado", usuarioId));
    }
}
