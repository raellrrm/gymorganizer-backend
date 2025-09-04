package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroUsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {
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

}
