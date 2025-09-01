package br.com.gymorganizer.api.controller.model.usuario;

import br.com.gymorganizer.api.controller.model.plano.PlanoIdInput;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UsuarioInput {

    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
    private String cpf;
    private PlanoIdInput plano;

}
