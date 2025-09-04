package br.com.gymorganizer.api.controller.model.pagamento;

import br.com.gymorganizer.api.controller.model.usuario.UsuarioModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class PagamentoModel {

    private Long id;
    private LocalDateTime dataPagamento;
    private BigDecimal valorPago;
    private UsuarioModel usuario;
}
