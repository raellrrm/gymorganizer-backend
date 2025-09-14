package br.com.gymorganizer.api.assembler.usuario;

import br.com.gymorganizer.api.controller.model.usuario.UsuarioInput;
import br.com.gymorganizer.api.controller.model.usuario.UsuarioUpdateInput;
import br.com.gymorganizer.domain.model.Plano;
import br.com.gymorganizer.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelDisassembler {

    @Autowired
    ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }


    public void copyToDomainObject(UsuarioUpdateInput usuarioUpdateInput, Usuario usuario) {
        modelMapper.map(usuarioUpdateInput, usuario);
    }
}
