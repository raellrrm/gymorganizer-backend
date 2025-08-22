package br.com.gymorganizer.domain.service;

import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import br.com.gymorganizer.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VerificacaoStatusService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Scheduled(cron = "0 0 1 * * *")
    public void verificarEAtualizarStatusAlunos() {
        System.out.println("INICIANDO TAREFA AGENDADA: Verificação de status dos alunos");
        List<StatusAluno> statusesParaVerificar = List.of(StatusAluno.ATIVO, StatusAluno.PENDENTE);

        List<Usuario> usuariosParaVerificar = usuarioRepository.findByStatusIn(statusesParaVerificar);
        List<Usuario> usuariosParaAtualizar = new ArrayList<>();
        LocalDate dataHoje = LocalDate.now();

        for (Usuario usuario : usuariosParaVerificar) {
            if (usuario.getDataVencimento() != null) {
                if (dataHoje.isAfter(usuario.getDataVencimento().plusDays(5))) {
                    if (usuario.getStatus() != StatusAluno.INATIVO) {
                        usuario.setStatus(StatusAluno.INATIVO);
                        usuariosParaAtualizar.add(usuario);
                    }
                } else if (dataHoje.isAfter(usuario.getDataVencimento())) {
                    if(usuario.getStatus() != StatusAluno.PENDENTE) {
                        usuario.setStatus(StatusAluno.PENDENTE);
                        usuariosParaAtualizar.add(usuario);
                    }
                }
            }
        }

        if (!usuariosParaAtualizar.isEmpty()) {
            usuarioRepository.saveAll(usuariosParaAtualizar);
            System.out.printf("%d usuários tiveram seu status atualizado.", usuariosParaAtualizar.size());
        } else {
            System.out.println("Nenhum usuário precisou ter o status atualizado.");
        }

        System.out.println("TAREFA AGENDADA FINALIZADA");
    }
}
