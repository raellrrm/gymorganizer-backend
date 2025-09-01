package br.com.gymorganizer.domain.repository;

import br.com.gymorganizer.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
