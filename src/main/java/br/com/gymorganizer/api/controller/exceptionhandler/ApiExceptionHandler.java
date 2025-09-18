package br.com.gymorganizer.api.controller.exceptionhandler;

import br.com.gymorganizer.domain.exception.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 404 - Recurso nao encontrado
     */
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ProblemType type = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente", ex.getResourcePath());

        Problem problem = createProblemBuilder(type, detail, (HttpStatus) status, LocalDateTime.now()).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    /**
     * 400 - Parâmetro do recurso inválido
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatchException(
                    (MethodArgumentTypeMismatchException) ex,
                    headers,
                    (HttpStatus) status,
                    request);
        }

        return handleExceptionInternal(ex, null, headers, status, request);
    }

    /**
     * 400 - Parâmetro inválido
     */
    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ProblemType type = ProblemType.PARAMETRO_INVALIDO;
        String detail = String.format(
                "O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. "
                        + "Corrija e informe um valor compatível com o tipo '%s'.",
                ex.getName(),
                ex.getValue(),
                ex.getRequiredType().getSimpleName());

        Problem problem = createProblemBuilder(type, detail, status, LocalDateTime.now()).build();

        return handleExceptionInternal(ex, problem, headers, status, request);

    }

    /**
     * 400 - Corpo da requisição incorreto
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause,
                    headers,
                    (HttpStatus) status,
                    request
            );
        }

        if (rootCause instanceof UnrecognizedPropertyException) {
            return handleUnrecognizedPropertyException((UnrecognizedPropertyException) rootCause,
                    headers,
                    (HttpStatus) status,
                    request);
        }

        ProblemType type = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está incorreto. Verifique erro de sintaxe.";

        Problem problem = createProblemBuilder(type, detail, (HttpStatus) status, LocalDateTime.now()).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    /**
     * 400 - Propriedade com valor inválido
     */
    private ResponseEntity<Object> handleInvalidFormatException(
            InvalidFormatException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        String path = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        ProblemType type = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' recebeu um valor '%s' que é " +
                        "de um tipo inválido. Corrija e informate um valor compatível com o tipo %s",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(type, detail, status, LocalDateTime.now()).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    /**
     * 400 - Propriedade desconhecida
     */
    private ResponseEntity<Object> handleUnrecognizedPropertyException(
            UnrecognizedPropertyException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        String path = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        ProblemType type = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' não existe. Corrija ou remova esta propriedade", path);

        Problem problem = createProblemBuilder(type, detail, status, LocalDateTime.now()).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

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

