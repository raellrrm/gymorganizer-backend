package br.com.gymorganizer.api.controller.model.plano;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlanoUpdateInput {

    @NotNull
    private Long plano;
}
