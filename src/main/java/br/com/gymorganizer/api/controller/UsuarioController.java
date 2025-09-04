package br.com.gymorganizer.api.controller;

import br.com.gymorganizer.api.assembler.pagamento.PagamentoModelAssembler;
import br.com.gymorganizer.api.assembler.pagamento.PagamentoModelDisassembler;
import br.com.gymorganizer.api.assembler.usuario.UsuarioModelAssembler;
import br.com.gymorganizer.api.assembler.usuario.UsuarioModelDisassembler;
import br.com.gymorganizer.api.controller.model.pagamento.PagamentoInput;
import br.com.gymorganizer.api.controller.model.pagamento.PagamentoModel;
import br.com.gymorganizer.api.controller.model.usuario.UsuarioInput;
import br.com.gymorganizer.api.controller.model.usuario.UsuarioModel;
import br.com.gymorganizer.domain.model.Pagamento;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import br.com.gymorganizer.domain.service.CadastroPagamentoService;
import br.com.gymorganizer.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //POST
    @PostMapping
    public UsuarioModel adicionar(@RequestBody UsuarioInput usuarioInput) {
        Usuario usuario = usuarioModelDisassembler.toDomainObject(usuarioInput);
        return usuarioModelAssembler.toModel(cadastroUsuarioService.salvar(usuario));
    }

    //POST
    @PostMapping("/{usuarioId}/pagar")
    public PagamentoModel pagar(@RequestBody PagamentoInput pagamentoInput, @PathVariable Long usuarioId) {
        Pagamento pagamento = pagamentoModelDisassembler.toDomainObject(pagamentoInput);

        return pagamentoModelAssembler.toModel(cadastroPagamentoService.pagar(pagamento, usuarioId));
    }

    //PUT
    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@RequestBody UsuarioInput usuarioInput, @PathVariable Long usuarioId) {
        Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);

        usuarioModelDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);

        return usuarioModelAssembler.toModel(cadastroUsuarioService.salvar(usuarioAtual));
    }

    //DELETE
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long usuarioId) {
        cadastroUsuarioService.excluir(usuarioId);
    }
}
