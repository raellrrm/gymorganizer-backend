package br.com.gymorganizer.domain.repository;

import br.com.gymorganizer.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("SELECT SUM(p.valorPago) FROM Pagamento p WHERE p.dataPagamento BETWEEN :inicio AND :fim")
    Optional<BigDecimal> sumValorPagoByDataPagamentoBetween(@Param("inicio") LocalDateTime inicio,
                                                            @Param("fim") LocalDateTime fim);
}
