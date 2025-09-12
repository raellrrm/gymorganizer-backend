package br.com.gymorganizer.api.controller.model.dashboard;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DashboardModel {

    private Long totalAlunosAtivos;
    private Long totalAlunosPendentes;
    private BigDecimal receitaDoMes;
}
