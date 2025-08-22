package br.com.gymorganizer.api.controller;

import br.com.gymorganizer.domain.model.Pagamento;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import br.com.gymorganizer.domain.service.CadastroPagamentoService;
import br.com.gymorganizer.domain.service.CadastroUsuarioService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por expor a API REST de Usuários.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroPagamentoService cadastroPagamentoService;

    /**
     * GET /usuarios
     * Retorna a lista de todos os usuários cadastrados.
     */
    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    /**
     * GET /usuarios/{usuarioId}
     * Busca um usuário específico pelo ID.
     */
    @GetMapping("/{usuarioId}")
    public Usuario buscar(@PathVariable Long usuarioId) {
        return cadastroUsuarioService.buscarOuFalhar(usuarioId);
    }

    /**
     * GET /usuarios/{usuarioId}
     * Busca um usuário específico pelo CPF.
     */
    @GetMapping("/cpf/{cpf}")
    public Usuario buscarPorCpf(@PathVariable String cpf) {
        return cadastroUsuarioService.buscarPorCpf(cpf);
    }

    /**
     * GET /status?status={status}
     * Retorna uma lista de todos os usuários que possuem o status passado o patâmetro
     */
    @GetMapping("/status")
    public List<Usuario> buscarPorStatus(@RequestParam String status) {
        return usuarioRepository.findByStatus(StatusAluno.valueOf(status.toUpperCase()));
    }

    /**
     * POST /usuarios
     * Cria um novo usuário.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario adicionar(@RequestBody Usuario usuario) {
        return cadastroUsuarioService.salvar(usuario);
    }

    /**
     * POST /usuarios/{usuarioId/pagamentos}
     * Cria um novo pagamento.
     */

    @PostMapping("/{usuarioId}/pagamentos")
    @ResponseStatus(HttpStatus.CREATED)
    public Pagamento efetuarPagamento(@RequestBody Pagamento pagamento, @PathVariable Long usuarioId) {
        return cadastroPagamentoService.pagar(pagamento, usuarioId);
    }

    /**
     * PUT /usuarios/{usuarioId}
     * Atualiza os dados de um usuário existente.
     */
    @PutMapping("/{usuarioId}")
    public Usuario atualizar(@RequestBody Usuario usuario, @PathVariable Long usuarioId) {
        Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);

        BeanUtils.copyProperties(usuario, usuarioAtual,
                "id", "dataCriacao", "dataNascimento", "cpf");

        return cadastroUsuarioService.salvar(usuarioAtual);
    }

    /**
     * DELETE /usuarios/{usuarioId}
     * Remove um usuário do sistema.
     */
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long usuarioId) {
        cadastroUsuarioService.excluir(usuarioId);
    }
}
