package br.com.gymorganizer.domain.repository;

import br.com.gymorganizer.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
