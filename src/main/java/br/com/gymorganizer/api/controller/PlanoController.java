package br.com.gymorganizer.api.controller;

import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.repository.PlanoRepository;
import br.com.gymorganizer.domain.service.CadastroPlanoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por expor a API REST de Planos.
 */
@RestController
@RequestMapping("/planos")
public class PlanoController {

    @Autowired
    CadastroPlanoService cadastroPlanoService;

    @Autowired
    PlanoRepository planoRepository;

    /**
     * GET /planos
     * Retorna a lista de todos os planos cadastrados
     */
    @GetMapping
    public List<Plano> todos() {
        return planoRepository.findAll();
    }

    /**
     * GET /planos/{planoId}
     * Busca um plano específico pelo ID.
     */
    @GetMapping("/{planoId}")
    public Plano buscar(@PathVariable Long planoId) {
        return cadastroPlanoService.buscarOuFalhar(planoId);
    }

    /**
     * Post /planos
     * Cria um novo plano
     */
    @PostMapping
    public Plano adicionar(@RequestBody Plano plano) {
        return cadastroPlanoService.salvar(plano);
    }

    /**
     * Post /planos/{planoId}
     * Atualiza dados de um plano existente
     */
    @PutMapping("/{planoId}")
    public Plano atualizar(@RequestBody Plano plano, @PathVariable Long planoId) {
        Plano planoAtual = cadastroPlanoService.buscarOuFalhar(planoId);

        BeanUtils.copyProperties(plano, planoAtual,
                "id", "dataCriacao");

        return cadastroPlanoService.salvar(planoAtual);
    }

    /**
     * Delete planos/{planoId}
     * Remove um plano do sistema
     */
    @DeleteMapping("/{planoId}")
    public void remover(@PathVariable Long planoId) {
        cadastroPlanoService.excluir(planoId);
    }
}
