package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.domain.exception.UsuarioAtivoException;
import br.com.gymorganizer.domain.model.Pagamento;
import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import br.com.gymorganizer.domain.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class CadastroPagamentoService {

    public static final String MSG_USUARIO_ATIVO = "O usuário id: %d já possui o status 'ativo'";
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private CadastroPlanoService cadastroPlanoService;

    public Pagamento pagar(Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        Plano plano = cadastroPlanoService.buscarOuFalhar(usuario.getPlano().getId());
        BigDecimal valorPlano = plano.getValor();

        Pagamento pagamento = new Pagamento();
        pagamento.setValorPago(valorPlano);

        if (usuario.getStatus() == StatusAluno.ATIVO) {
            throw new UsuarioAtivoException(String.format(MSG_USUARIO_ATIVO, usuarioId));
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
