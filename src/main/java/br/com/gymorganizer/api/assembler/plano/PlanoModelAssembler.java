package br.com.gymorganizer.api.assembler.plano;

import br.com.gymorganizer.api.controller.model.plano.PlanoModel;
import br.com.gymorganizer.domain.model.Plano;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanoModelAssembler {

    @Autowired
    ModelMapper modelMapper;

    public PlanoModel toModel(Plano plano) {
        return modelMapper.map(plano, PlanoModel.class);
    }

    public List<PlanoModel> toCollectModel(List<Plano> planos) {
        return planos.stream()
                .map(plano -> toModel(plano))
                .collect(Collectors.toList());
    }
}
