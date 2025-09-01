package br.com.gymorganizer.api.controller.model.plano;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PlanoInput {

    private String nome;
    private BigDecimal valor;
    private Integer duracaoEmDias;
}
