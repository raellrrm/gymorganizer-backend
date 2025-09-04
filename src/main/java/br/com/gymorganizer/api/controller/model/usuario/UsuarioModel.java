package br.com.gymorganizer.api.controller.model.usuario;

import br.com.gymorganizer.api.controller.model.plano.PlanoModel;
import br.com.gymorganizer.domain.model.enums.StatusAluno;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UsuarioModel {

    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String cpf;
    private StatusAluno statusAluno;
    private PlanoModel plano;
    private LocalDate dataVencimento;
}
