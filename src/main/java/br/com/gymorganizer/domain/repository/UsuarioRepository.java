package br.com.gymorganizer.domain.repository;

import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCpfAndIdNot(String cpf, Long usuarioId);
    boolean existsByEmailAndIdNot(String email, Long usuarioId);
    List<Usuario> findByStatusIn(List<StatusAluno> statuses);
    List<Usuario> findByStatus(StatusAluno statusAluno);
    Optional<Usuario> findByCpf(String cpf);
}
