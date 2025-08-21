package br.com.gymorganizer.domain.repository;

import br.com.gymorganizer.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCpfAndIdNot(String cpf, Long usuarioId);
    boolean existsByEmailAndIdNot(String email, Long usuarioId);
    Optional<Usuario> findByCpf(String cpf);
}
