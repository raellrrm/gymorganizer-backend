package br.com.gymorganizer.api.assembler.pagamento;

import br.com.gymorganizer.api.controller.model.pagamento.PagamentoModel;
import br.com.gymorganizer.domain.model.Pagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PagamentoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PagamentoModel toModel(Pagamento pagamento) {
        return modelMapper.map(pagamento, PagamentoModel.class);
    }

}
