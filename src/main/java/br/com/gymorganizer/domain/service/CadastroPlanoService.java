package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.domain.exception.EntidadeEmUsoException;
import br.com.gymorganizer.domain.exception.PlanoNaoEncontradoException;
import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.repository.PlanoRepository;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CadastroPlanoService {

    public static final String MSG_PLANO_EM_USO = "Plano id: %d  não pode ser excluído pois possui assinantes.";
    @Autowired
    PlanoRepository planoRepository;

    public Plano salvar(Plano plano) {
        return planoRepository.save(plano);
    }

    public Plano buscarOuFalhar(Long planoId) {
        return planoRepository.findById(planoId).orElseThrow(
                () -> new PlanoNaoEncontradoException(planoId)
        );
    }

    public void excluir(Long planoId) {
        try {
            Plano plano = buscarOuFalhar(planoId);
            planoRepository.deleteById(plano.getId());
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_PLANO_EM_USO, planoId));
        }

    }
}
