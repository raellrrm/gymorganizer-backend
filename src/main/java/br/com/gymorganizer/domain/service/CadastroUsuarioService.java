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

    public void excluir(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

}
