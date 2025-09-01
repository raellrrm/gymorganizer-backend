package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.repository.PlanoRepository;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPlanoService {

    @Autowired
    PlanoRepository planoRepository;

    public Plano salvar(Plano plano) {
        return planoRepository.save(plano);
    }

    public Plano buscarOuFalhar(Long planoId) {
        return planoRepository.findById(planoId).orElseThrow(
                () -> new IllegalArgumentException()
        );
    }

    public void excluir(Long planoId) {
        planoRepository.deleteById(planoId);
    }
}
