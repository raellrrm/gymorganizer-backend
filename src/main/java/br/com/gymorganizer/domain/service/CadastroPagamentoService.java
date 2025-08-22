package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.domain.model.Pagamento;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import br.com.gymorganizer.domain.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    CadastroUsuarioService cadastroUsuarioService;

    public Pagamento pagar(Pagamento pagamento, Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        pagamento.setUsuario(usuario);
        pagamento = pagamentoRepository.save(pagamento);
        usuario.setStatus(StatusAluno.ATIVO);
        cadastroUsuarioService.salvar(usuario);

        return pagamento;
    }
}
