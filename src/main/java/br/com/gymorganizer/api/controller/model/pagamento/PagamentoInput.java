package br.com.gymorganizer.api.controller.model.pagamento;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PagamentoInput {

    private BigDecimal valorPago;
}
