package br.com.gymorganizer.api.controller.model.usuario;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioUpdateInput {

    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
}
