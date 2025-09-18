package br.com.gymorganizer.api.controller.exceptionhandler;

import br.com.gymorganizer.domain.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 400 - Usuário já está ativo.
     */
    @ExceptionHandler(UsuarioAtivoException.class)
    public ResponseEntity<?> handleUsuarioAtivoException(UsuarioAtivoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType type = ProblemType.USUARIO_JA_ATIVO;

        Problem problem = createProblemBuilder(type, ex.getMessage(), status, LocalDateTime.now()).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    /**
     * 400 - Atributo único já em uso.
     */
    @ExceptionHandler(AtributoEmUsoException.class)
    public ResponseEntity<?> handleAtributoEmUso(AtributoEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType type = ProblemType.ATRIBUTO_EM_USO;

        Problem problem = createProblemBuilder(type, ex.getMessage(), status, LocalDateTime.now()).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    /**
     * 400 - Entidade vinculada a outra (em uso).
     */
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType type = ProblemType.ENTIDADE_EM_USO;

        Problem problem = createProblemBuilder(type, ex.getMessage(), status, LocalDateTime.now()).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    /**
     * 404 - Entidade não encontrada.
     */
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.RECURSO_NAO_ENCONTRADO;

        Problem problem = createProblemBuilder(type, ex.getMessage(), status, LocalDateTime.now()).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    /**
     * Trata exceção de erro de negócio (HTTP 400).
     */
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(
            NegocioException ex,
            WebRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType type = ProblemType.ERRO_DE_NEGOCIO;

        Problem problem = createProblemBuilder(type, ex.getMessage(), status, LocalDateTime.now()).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    /**
     * Trata exceções não mapeadas ou sem corpo definido.
     * Retorna erro padronizado no formato {@link Problem}.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        if (body == null) {
            HttpStatus status = HttpStatus.valueOf(statusCode.value());
            body = Problem.builder()
                    .status(statusCode.value())
                    .title(status.getReasonPhrase())
                    .detail(ex.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        return new ResponseEntity<>(body, headers, statusCode);
    }

    /**
     * Constrói um {@link Problem} com dados consistentes.
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

