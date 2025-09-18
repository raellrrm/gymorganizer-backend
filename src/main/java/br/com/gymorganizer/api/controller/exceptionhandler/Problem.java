package br.com.gymorganizer.api.controller.exceptionhandler;

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
    private List<Field> fields;

    @Builder
    @Getter
    public static class Field {
        private String name;
        private String userMessage;
    }
}
