package br.com.gymorganizer.domain.exception;

public class UsuarioJaAtivoException extends NegocioException {
    public UsuarioJaAtivoException(String message) {
        super(message);
    }

    public UsuarioJaAtivoException(Long usuarioId) {
        super(String.format("O usuário id: %d ja está com o status 'ATIVO'", usuarioId));
    }
}
