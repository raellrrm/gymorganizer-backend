package br.com.gymorganizer.api.controller;

import br.com.gymorganizer.api.assembler.plano.PlanoModelAssembler;
import br.com.gymorganizer.api.assembler.plano.PlanoModelDisassembler;
import br.com.gymorganizer.api.controller.model.plano.PlanoInput;
import br.com.gymorganizer.api.controller.model.plano.PlanoModel;
import br.com.gymorganizer.api.controller.model.usuario.UsuarioInput;
import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.repository.PlanoRepository;
import br.com.gymorganizer.domain.service.CadastroPlanoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
public class PlanoController {

    @Autowired
    private CadastroPlanoService cadastroPlanoService;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private PlanoModelAssembler planoModelAssembler;

    @Autowired
    private PlanoModelDisassembler planoModelDisassembler;

    //GET
    @GetMapping
    public List<PlanoModel> todos() {
        return planoModelAssembler.toCollectModel(planoRepository.findAll());
    }

    //GET
    @GetMapping("/{planoId}")
    public PlanoModel buscar(@PathVariable Long planoId) {
        return planoModelAssembler.toModel(cadastroPlanoService.buscarOuFalhar(planoId));
    }

    //POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanoModel adicionar(@RequestBody @Valid PlanoInput planoInput) {
        Plano plano = planoModelDisassembler.toDomainObject(planoInput);
        return planoModelAssembler.toModel(cadastroPlanoService.salvar(plano));
    }

    //PUT
    @PutMapping("/{planoId}")
    public PlanoModel atualizar(@RequestBody PlanoInput planoInput, @PathVariable Long planoId) {
        Plano planoAtual = cadastroPlanoService.buscarOuFalhar(planoId);

        planoModelDisassembler.copyToDomainObject(planoInput, planoAtual);

        return planoModelAssembler.toModel(cadastroPlanoService.salvar(planoAtual));
    }

    //DELETE
    @DeleteMapping("/{planoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long planoId) {
        cadastroPlanoService.excluir(planoId);
    }
}
