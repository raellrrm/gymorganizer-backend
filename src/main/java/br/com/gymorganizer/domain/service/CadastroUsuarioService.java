package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.domain.exception.CpfEmUsoException;
import br.com.gymorganizer.domain.exception.CpfNaoEncontradoException;
import br.com.gymorganizer.domain.exception.EmailEmUsoException;
import br.com.gymorganizer.domain.exception.UsuarioNaoEncontradoException;
import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroUsuarioService {

    public static final String MSG_CPF_EM_USO = "Este CPF já está em uso.";
    public static final String MSG_EMAIL_EM_USO = "Este e-mail já está em uso.";
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CadastroPlanoService cadastroPlanoService;

    public Usuario salvar(Usuario usuario) {
        usuario = verificarEmailECpf(usuario);

        Plano plano = cadastroPlanoService.buscarOuFalhar(usuario.getPlano().getId());

        usuario.setPlano(plano);

        if(usuario.getId() == null) {
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

    private Usuario verificarEmailECpf(Usuario usuario) {
        if(usuarioRepository.existsByCpfAndIdNot(usuario.getCpf(), usuario.getId())) {
            throw new CpfEmUsoException(MSG_CPF_EM_USO);
        }
        if(usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), usuario.getId())) {
            throw new EmailEmUsoException(MSG_EMAIL_EM_USO);
        }

        return usuario;
    }

}
