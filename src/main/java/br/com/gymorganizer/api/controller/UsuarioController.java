package br.com.gymorganizer.api.controller;

import br.com.gymorganizer.api.assembler.pagamento.PagamentoModelAssembler;
import br.com.gymorganizer.api.assembler.pagamento.PagamentoModelDisassembler;
import br.com.gymorganizer.api.assembler.usuario.UsuarioModelAssembler;
import br.com.gymorganizer.api.assembler.usuario.UsuarioModelDisassembler;
import br.com.gymorganizer.api.controller.model.pagamento.PagamentoInput;
import br.com.gymorganizer.api.controller.model.pagamento.PagamentoModel;
import br.com.gymorganizer.api.controller.model.plano.PlanoUpdateInput;
import br.com.gymorganizer.api.controller.model.usuario.UsuarioInput;
import br.com.gymorganizer.api.controller.model.usuario.UsuarioModel;
import br.com.gymorganizer.api.controller.model.usuario.UsuarioUpdateInput;
import br.com.gymorganizer.api.controller.model.usuario.UsuarioUpdatePatchInput;
import br.com.gymorganizer.domain.model.Pagamento;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import br.com.gymorganizer.domain.service.CadastroPagamentoService;
import br.com.gymorganizer.domain.service.CadastroUsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private UsuarioModelDisassembler usuarioModelDisassembler;

    @Autowired
    private PagamentoModelAssembler pagamentoModelAssembler;

    @Autowired
    private PagamentoModelDisassembler pagamentoModelDisassembler;

    @Autowired
    private CadastroPagamentoService cadastroPagamentoService;

    //GET
    @GetMapping
    public List<UsuarioModel> todos() {
        return usuarioModelAssembler.toCollectModel(usuarioRepository.findAll());
    }

    //GET
    @GetMapping("/{usuarioId}")
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
        return usuarioModelAssembler.toModel(cadastroUsuarioService.buscarOuFalhar(usuarioId));
    }

    //GET
    @GetMapping("/status")
    public List<UsuarioModel> buscarPorStatus(@RequestParam String status) {
        return usuarioModelAssembler.toCollectModel(usuarioRepository.findByStatus(StatusAluno.valueOf(status.toUpperCase())));
    }

    //GET
    @GetMapping("/cpf/{cpfUsuario}")
    public UsuarioModel buscarPorCpf(@PathVariable String cpfUsuario) {
        return usuarioModelAssembler.toModel(cadastroUsuarioService.buscarPorCpf(cpfUsuario));
    }


    //POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = usuarioModelDisassembler.toDomainObject(usuarioInput);
        return usuarioModelAssembler.toModel(cadastroUsuarioService.salvar(usuario));
    }

    //POST
    @PostMapping("/{usuarioId}/pagamento")
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoModel pagamento(@RequestBody PagamentoInput pagamentoInput, @PathVariable Long usuarioId) {
        Pagamento pagamento = pagamentoModelDisassembler.toDomainObject(pagamentoInput);

        return pagamentoModelAssembler.toModel(cadastroPagamentoService.pagar(pagamento, usuarioId));
    }

    //PUT
    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@RequestBody @Valid UsuarioUpdateInput usuarioUpdateInput, @PathVariable Long usuarioId) {
        Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);

        usuarioModelDisassembler.copyToDomainObject(usuarioUpdateInput, usuarioAtual);

        return usuarioModelAssembler.toModel(cadastroUsuarioService.salvar(usuarioAtual));
    }

    //PUT
    @PutMapping("/{usuarioId}/plano")
    public UsuarioModel atualizarPlano(@RequestBody PlanoUpdateInput planoUpdateInput, @PathVariable Long usuarioId) {
        return usuarioModelAssembler.toModel(cadastroUsuarioService.alterarPlano(planoUpdateInput, usuarioId));
    }

    //PATCH
    @PatchMapping("/{usuarioId}")
    public UsuarioModel atualizarParcial(@RequestBody  @Valid UsuarioUpdatePatchInput usuarioUpdatePatchInput,
                                         @PathVariable Long usuarioId) {

        return usuarioModelAssembler.toModel(cadastroUsuarioService.alterarParcial(usuarioUpdatePatchInput, usuarioId));
    }

    //DELETE
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long usuarioId) {
        cadastroUsuarioService.excluir(usuarioId);
    }
}
