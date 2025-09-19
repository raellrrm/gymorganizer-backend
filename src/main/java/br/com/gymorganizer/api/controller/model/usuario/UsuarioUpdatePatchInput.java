package br.com.gymorganizer.api.controller.model.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UsuarioUpdatePatchInput {

    private String nome;

    private String sobrenome;

    private String telefone;

    @Email
    private String email;
}
