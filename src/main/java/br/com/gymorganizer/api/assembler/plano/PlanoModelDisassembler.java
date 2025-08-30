package br.com.gymorganizer.api.assembler.plano;

import br.com.gymorganizer.api.controller.model.plano.PlanoInput;
import br.com.gymorganizer.domain.model.Plano;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlanoModelDisassembler {

    @Autowired
    ModelMapper modelMapper;

    public Plano toDomainObject(PlanoInput planoInput) {
        return modelMapper.map(planoInput, Plano.class);
    }

    public void copyToDomainObject(PlanoInput planoInput, Plano plano) {
        modelMapper.map(planoInput, plano);
    }
}
