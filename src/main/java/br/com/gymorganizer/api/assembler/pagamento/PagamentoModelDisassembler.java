package br.com.gymorganizer.api.assembler.pagamento;

import br.com.gymorganizer.api.controller.model.pagamento.PagamentoInput;
import br.com.gymorganizer.domain.model.Pagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagamentoModelDisassembler {

    @Autowired
    ModelMapper modelMapper;

    public Pagamento toDomainObject(PagamentoInput pagamentoInput) {
        return modelMapper.map(pagamentoInput, Pagamento.class);
    }
}
