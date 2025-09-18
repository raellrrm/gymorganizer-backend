package br.com.gymorganizer.api.controller.model.usuario;

import br.com.gymorganizer.api.controller.model.plano.PlanoIdInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UsuarioInput {

    @NotBlank
    private String nome;

    @NotBlank
    private String sobrenome;

    @NotNull
    private LocalDate dataNascimento;

    @NotBlank
    private String telefone;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 11, min = 11)
    private String cpf;

    @Valid
    @NotNull
    private PlanoIdInput plano;

}
