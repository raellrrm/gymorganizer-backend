package br.com.gymorganizer.api.controller;

import br.com.gymorganizer.domain.service.RelatorioPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioPdfService relatorioPdfService;

    @GetMapping("/usuarios/{usuarioId}/pagamentos")
    public ResponseEntity<byte[]> gerarRelatorioPagamentos(@PathVariable Long usuarioId) {
        try {
            byte[] pdfBytes = relatorioPdfService.gerarPdfPagamentosUsuario(usuarioId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            headers.setContentDispositionFormData("inline", "relatorio_pagamentos_" + usuarioId + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

