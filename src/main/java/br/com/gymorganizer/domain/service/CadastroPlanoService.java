package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.domain.exception.EntidadeEmUsoException;
import br.com.gymorganizer.domain.exception.PlanoNaoEncontrado;
import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CadastroPlanoService {

    public static final String ENTIDADE_PLANO_EM_USO = "Este plano id: %d " +
            "possui assinantes ativos";
    @Autowired
    PlanoRepository planoRepository;

    public Plano salvar(Plano plano) {
        return planoRepository.save(plano);
    }

    public void excluir(long planoId) {
        Plano plano = buscarOuFalhar(planoId);
        try {
            planoRepository.deleteById(planoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(ENTIDADE_PLANO_EM_USO, planoId));
        }

    }

    public Plano buscarOuFalhar(Long planoId) {
        return planoRepository.findById(planoId).orElseThrow(
                () -> new PlanoNaoEncontrado(planoId)
        );
    }
}
