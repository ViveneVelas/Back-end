package com.velas.vivene.inventory.manager.dto.quantidadevendasseismeses;

import com.velas.vivene.inventory.manager.entity.view.QuantidadeVendasSeisMeses;
import org.springframework.stereotype.Component;

@Component
public class QuantidadeVendasSeisMesesMapper {

    public QuantidadeVendasSeisMesesResponse toDto(QuantidadeVendasSeisMeses quantidadeVendasSeisMeses) {
        if (quantidadeVendasSeisMeses == null) {
            return null;
        }

        QuantidadeVendasSeisMesesResponse response = new QuantidadeVendasSeisMesesResponse();
        response.setMesAno(quantidadeVendasSeisMeses.getMesAno());
        response.setQtdPedidosConcluidos(quantidadeVendasSeisMeses.getQtdPedidosConcluidos());

        return response;
    }
}
