package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.api.controller.model.plano.PlanoUpdateInput;
import br.com.gymorganizer.api.controller.model.usuario.UsuarioUpdatePatchInput;
import br.com.gymorganizer.domain.exception.*;
import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class CadastroUsuarioService {

    public static final String MSG_CPF_EM_USO = "Este CPF já está em uso.";
    public static final String MSG_EMAIL_EM_USO = "Este e-mail já está em uso.";
    public static final String MSG_ERRO_ATUALIZAR_PLANO_STATUS_COMO_ATIVO = "Não é possível alterar o plano de um usuário ativo. A alteração deve ser feita quando o ciclo de pagamento atual terminar e o status for 'PENDENTE'.";
    public static final String MSG_JA_ESTA_VINCULADO_AO_PLANO = "O usuário já está vinculado a este plano";
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CadastroPlanoService cadastroPlanoService;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        verificarEmailECpf(usuario);

        Plano plano = cadastroPlanoService.buscarOuFalhar(usuario.getPlano().getId());

        usuario.setPlano(plano);

        if (usuario.getId() == null) {
            usuario.setStatus(StatusAluno.PENDENTE);
        }

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> todos(String cpf, StatusAluno status) {
        boolean temCpf = StringUtils.hasText(cpf);
        boolean temStatus = status != null;

        if (temCpf && temStatus) {
            return usuarioRepository.findByCpfAndStatus(cpf, status);
        }

        if (temCpf) {
            return usuarioRepository.findByCpf(cpf)
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        }

        if (temStatus) {
            return usuarioRepository.findByStatus(status);
        }

        return usuarioRepository.findAll();
    }

    public Usuario buscarPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf).orElseThrow((
                () -> new CpfNaoEncontradoException(cpf)
        ));
    }

    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(
                () -> new UsuarioNaoEncontradoException(usuarioId)
        );
    }

    @Transactional
    public void inativar(Long usuarioId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        usuario.setStatus(StatusAluno.INATIVO);
        usuario.setDataVencimento(null);

        usuarioRepository.deleteById(usuario.getId());
    }

    @Transactional
    public Usuario alterarPlano(PlanoUpdateInput planoUpdateInput, Long usuarioId) {
        Long planoId = planoUpdateInput.getPlano();
        Plano plano = cadastroPlanoService.buscarOuFalhar(planoId);
        Usuario usuario = buscarOuFalhar(usuarioId);

        if (plano.getNome().equals(usuario.getPlano().getNome())) {
            throw new NegocioException(MSG_JA_ESTA_VINCULADO_AO_PLANO);
        }

        if (usuario.getStatus().equals(StatusAluno.ATIVO)) {
            throw new NegocioException(MSG_ERRO_ATUALIZAR_PLANO_STATUS_COMO_ATIVO);
        }

        usuario.setPlano(plano);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario alterarParcial(UsuarioUpdatePatchInput patchInput, Long usuarioId) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        if (patchInput.getNome() != null) {
            if (patchInput.getNome().isBlank()) {
                throw new NegocioException("Nome não pode ser vazio");
            }
            usuario.setNome(patchInput.getNome());
        }

        if (patchInput.getSobrenome() != null) {
            if (patchInput.getSobrenome().isBlank()) {
                throw new NegocioException("Sobrenome não pode ser vazio");
            }
            usuario.setSobrenome(patchInput.getSobrenome());
        }

        if (patchInput.getTelefone() != null) {
            if (patchInput.getTelefone().isBlank()) {
                throw new NegocioException("Telefone não pode ser vazio");
            }
            usuario.setTelefone(patchInput.getTelefone());
        }

        if (patchInput.getEmail() != null) {
            if (patchInput.getEmail().isBlank()) {
                throw new NegocioException("Email não pode ser vazio");
            }
            usuario.setEmail(patchInput.getEmail());
        }

        return salvar(usuario);
    }

    private void verificarEmailECpf(Usuario usuario) {
        if (usuarioRepository.existsByCpfUnfiltered(usuario.getCpf(), usuario.getId()) > 0) {
            throw new CpfEmUsoException(MSG_CPF_EM_USO);
        }

        if (usuarioRepository.existsByEmailUnfiltered(usuario.getEmail(), usuario.getId()) > 0) {
            throw new EmailEmUsoException(MSG_EMAIL_EM_USO);
        }
    }
}

