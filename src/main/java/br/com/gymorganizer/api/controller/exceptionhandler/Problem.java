package br.com.gymorganizer.api.controller.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Problem {
    private Integer status;
    private LocalDateTime timestamp;
    private String type;
    private String title;
    private String detail;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Field> fields;

    @Builder
    @Getter
    public static class Field {
        private String name;
        private String userMessage;
    }
}
