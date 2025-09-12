package br.com.gymorganizer.api.controller;

import br.com.gymorganizer.api.controller.model.dashboard.DashboardModel;
import br.com.gymorganizer.domain.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public DashboardModel getDashboardData() {
        return  dashboardService.getDadosDashboard();
    }
}
