package br.com.gymorganizer.api.controller.model.plano;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlanoIdInput {

    @NotNull
    private Long id;
}
