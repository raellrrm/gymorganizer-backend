package br.com.gymorganizer.api.controller.exceptionhandler;

import br.com.gymorganizer.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * Classe responsável por capturar e tratar exceções da aplicação.
 *
 * Ocorre ao tentar realizar um pagamento quando o usuário já está com o status de pagamento ativo
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsuarioJaAtivoException.class)
    public ResponseEntity<?> handleUsuarioJaAtivoException(UsuarioJaAtivoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400
        ProblemType type = ProblemType.USUARIO_JA_ATIVO;

        Problem problem = createProblemBuilder(type, ex.getMessage(), status, LocalDateTime.now()).build();
        return ResponseEntity.status(status).body(problem);
    }

    /**
     * Trata a exceção AtributoEmUsoException.
     *
     * Ocorre quando algum atributo que deveria ser único já está em uso.
     */
    @ExceptionHandler(AtributoEmUsoException.class)
    public ResponseEntity<?> handleAtributoEmUso(AtributoEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400
        ProblemType type = ProblemType.ATRIBUTO_EM_USO;

        Problem problem = createProblemBuilder(type, ex.getMessage(), status, LocalDateTime.now()).build();
        return ResponseEntity.status(status).body(problem);
    }

    /**
     * Trata a exceção EntidadeEmUsoException.
     *
     * Ocorre quando uma entidade não pode ser removida/alterada
     * por estar vinculada a outra (ex.: chave estrangeira).
     */
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400
        ProblemType type = ProblemType.ENTIDADE_EM_USO;

        Problem problem = createProblemBuilder(type, ex.getMessage(), status, LocalDateTime.now()).build();
        return ResponseEntity.status(status).body(problem);
    }

    /**
     * Trata a exceção EntidadeNaoEncontradaException.
     *
     * Ocorre quando uma entidade buscada por ID não é encontrada no banco.
     */
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404
        ProblemType type = ProblemType.RECURSO_NAO_ENCONTRADO;

        Problem problem = createProblemBuilder(type, ex.getMessage(), status, LocalDateTime.now()).build();
        return ResponseEntity.status(status).body(problem);
    }

    /**
     * Método auxiliar para construir objetos do tipo Problem (corpo do erro).
     */
    public Problem.ProblemBuilder createProblemBuilder(
            ProblemType type, String detail, HttpStatus status, LocalDateTime timestamp) {

        return Problem.builder()
                .title(type.getTitle())
                .detail(detail)
                .type(type.getUri())
                .status(status.value())
                .timestamp(timestamp);
    }
}
