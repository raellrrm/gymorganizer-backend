package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.api.controller.model.dashboard.DashboardModel;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import br.com.gymorganizer.domain.repository.PagamentoRepository;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class DashboardService {


    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public DashboardModel getDadosDashboard() {

        DashboardModel dashboardModel = new DashboardModel();

        Long totalAlunosAtivos = usuarioRepository.countByStatus(StatusAluno.ATIVO);
        Long totalAlunosPendentes = usuarioRepository.countByStatus(StatusAluno.PENDENTE);

        dashboardModel.setTotalAlunosAtivos(totalAlunosAtivos);
        dashboardModel.setTotalAlunosPendentes(totalAlunosPendentes);

        LocalDateTime inicioDoMes = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime fimDoMes = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);

        BigDecimal receitaDoMes = pagamentoRepository.sumValorPagoByDataPagamentoBetween(inicioDoMes, fimDoMes)
                .orElse(BigDecimal.ZERO);

        dashboardModel.setReceitaDoMes(receitaDoMes);

        return dashboardModel;

    }
}
