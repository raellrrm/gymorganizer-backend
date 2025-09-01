package br.com.gymorganizer.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@Entity
public class Plano {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Id
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal valor;

    @CreationTimestamp
    @Column(nullable = false, name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(nullable = false, name = "duracao_em_dias")
    private Integer duracaoEmDias;
}
