package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.api.controller.model.plano.PlanoUpdateInput;
import br.com.gymorganizer.api.controller.model.usuario.UsuarioUpdateInput;
import br.com.gymorganizer.domain.exception.*;
import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

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

    public Usuario alterarParcial(Map<String, Object> fields, Long usuarioId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        merge(fields, usuario);

        return salvar(usuario);
    }

    private void merge(Map<String, Object> fields, Usuario usuarioDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        UsuarioUpdateInput usuarioConvert = objectMapper.convertValue(fields, UsuarioUpdateInput.class);

        fields.forEach((propertyName, propertyValue) -> {
            Field field = ReflectionUtils.findField(Usuario.class, propertyName);
            Field sourceField = ReflectionUtils.findField(UsuarioUpdateInput.class, propertyName);

            field.setAccessible(true);
            sourceField.setAccessible(true);

            Object newValue = ReflectionUtils.getField(sourceField, usuarioConvert);

            ReflectionUtils.setField(field, usuarioDestino, newValue);
        });
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
