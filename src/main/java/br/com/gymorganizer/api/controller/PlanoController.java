package br.com.gymorganizer.api.controller;

import br.com.gymorganizer.api.assembler.plano.PlanoModelAssembler;
import br.com.gymorganizer.api.controller.model.plano.PlanoModel;
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

    @Autowired
    PlanoModelAssembler planoModelAssembler;

    /**
     * GET /planos
     * Retorna a lista de todos os planos cadastrados
     */
    @GetMapping
    public List<PlanoModel> todos() {
        return planoModelAssembler.toCollectModel(planoRepository.findAll());
    }

    /**
     * GET /planos/{planoId}
     * Busca um plano específico pelo ID.
     */
    @GetMapping("/{planoId}")
    public PlanoModel buscar(@PathVariable Long planoId) {
        return planoModelAssembler.toModel(cadastroPlanoService.buscarOuFalhar(planoId));
    }

    /**
     * Post /planos
     * Cria um novo plano
     */
    @PostMapping
    public PlanoModel adicionar(@RequestBody Plano plano) {
        return planoModelAssembler.toModel(cadastroPlanoService.salvar(plano));
    }

    /**
     * Post /planos/{planoId}
     * Atualiza dados de um plano existente
     */
    @PutMapping("/{planoId}")
    public PlanoModel atualizar(@RequestBody Plano plano, @PathVariable Long planoId) {
        Plano planoAtual = cadastroPlanoService.buscarOuFalhar(planoId);

        BeanUtils.copyProperties(plano, planoAtual,
                "id", "dataCriacao");

        return planoModelAssembler.toModel(cadastroPlanoService.salvar(planoAtual));
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
