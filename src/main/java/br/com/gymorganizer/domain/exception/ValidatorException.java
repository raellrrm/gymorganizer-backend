package br.com.gymorganizer.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class ValidatorException extends RuntimeException {

    private BindingResult bindingResult;
}
