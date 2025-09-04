package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroUsuarioService {

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

    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(
                () -> new IllegalArgumentException()
        );
    }

    public void excluir(Long usuarioId) {
        try {
            Usuario usuario = buscarOuFalhar(usuarioId);
            usuarioRepository.deleteById(usuario.getId());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    private Usuario verificarEmailECpf(Usuario usuario) {
        if(usuarioRepository.existsByCpfAndIdNot(usuario.getCpf(), usuario.getId())) {
            throw new IllegalArgumentException();
        }
        if(usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), usuario.getId())) {
            throw new IllegalArgumentException();
        }

        return usuario;
    }

}
