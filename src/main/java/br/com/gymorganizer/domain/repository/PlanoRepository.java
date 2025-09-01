package br.com.gymorganizer.domain.repository;

import br.com.gymorganizer.domain.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanoRepository extends JpaRepository<Plano, Long> {
}
