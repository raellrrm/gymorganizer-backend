package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.domain.model.Pagamento;
import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import br.com.gymorganizer.domain.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CadastroPagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    CadastroUsuarioService cadastroUsuarioService;

    public Pagamento pagar(Pagamento pagamento, Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);

        if (usuario.getStatus() == StatusAluno.ATIVO) {
            throw new IllegalArgumentException();
        }

        atualizarStatusUsuario(usuario);
        pagamento.setUsuario(usuario);

        return pagamentoRepository.save(pagamento);
    }

    private void atualizarStatusUsuario(Usuario usuario) {
        Plano plano = usuario.getPlano();

        usuario.setStatus(StatusAluno.ATIVO);

        usuario.setDataVencimento(LocalDate.now().plusDays(plano.getDuracaoEmDias()));

        cadastroUsuarioService.salvar(usuario);
    }
}
