package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.api.controller.model.plano.PlanoUpdateInput;
import br.com.gymorganizer.api.controller.model.usuario.UsuarioUpdateInput;
import br.com.gymorganizer.api.controller.model.usuario.UsuarioUpdatePatchInput;
import br.com.gymorganizer.domain.exception.*;
import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import java.lang.reflect.Field;
import java.util.Map;

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

    public Usuario salvar(Usuario usuario) {
        usuario = verificarEmailECpf(usuario);

        Plano plano = cadastroPlanoService.buscarOuFalhar(usuario.getPlano().getId());

        usuario.setPlano(plano);

        if (usuario.getId() == null) {
            usuario.setStatus(StatusAluno.PENDENTE);
        }

        return usuarioRepository.save(usuario);
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

    public void excluir(Long usuarioId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        usuario.setStatus(StatusAluno.INATIVO);
        usuario.setDataVencimento(null);
        usuarioRepository.deleteById(usuario.getId());
    }

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

    private Usuario verificarEmailECpf(Usuario usuario) {
        if (usuarioRepository.existsByCpfAndIdNot(usuario.getCpf(), usuario.getId())) {
            throw new CpfEmUsoException(MSG_CPF_EM_USO);
        }
        if (usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), usuario.getId())) {
            throw new EmailEmUsoException(MSG_EMAIL_EM_USO);
        }

        return usuario;
    }

}
