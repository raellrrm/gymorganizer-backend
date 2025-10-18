package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.domain.model.Pagamento;
import br.com.gymorganizer.domain.model.Usuario;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelatorioPdfService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;


    public byte[] gerarPdfPagamentosUsuario(Long usuarioId) throws IOException {

        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        List<Pagamento> pagamentos = usuario.getPagamentos();

        Context context = new Context();
        context.setVariable("usuario", usuario);
        context.setVariable("pagamentos", pagamentos);
        context.setVariable("dataGeracao", LocalDateTime.now());

        String html = templateEngine.process("relatorio_pagamentos", context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, null);
        builder.toStream(outputStream);
        builder.run();


        return outputStream.toByteArray();
    }
}