package br.com.gymorganizer.domain.repository;

import br.com.gymorganizer.domain.model.Usuario;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmailAndIdNot(String email, Long usuarioId);
    boolean existsByCpfAndIdNot(String cpf, Long usuarioId);
    List<Usuario> findByStatusIn(List<StatusAluno> statuses);
    List<Usuario> findByStatus(StatusAluno statusAluno);
    Optional<Usuario> findByCpf(String cpf);
    List<Usuario> findByCpfAndStatus(String cpf, StatusAluno status);
    Long countByStatus(StatusAluno statusAluno);
    @Query(value = "SELECT EXISTS(SELECT 1 FROM usuario WHERE email = :email AND (:id IS NULL OR id <> :id))",
            nativeQuery = true)
    Integer existsByEmailUnfiltered(@Param("email") String email, @Param("id") Long id);

    /**
     * Verifica se um CPF já existe em QUALQUER usuário (ativo ou inativo),
     * retornando um Integer (0 ou 1) para evitar problemas de cast do JDBC.
     */
    @Query(value = "SELECT EXISTS(SELECT 1 FROM usuario WHERE cpf = :cpf AND (:id IS NULL OR id <> :id))",
            nativeQuery = true)
    Integer existsByCpfUnfiltered(@Param("cpf") String cpf, @Param("id") Long id);
}
