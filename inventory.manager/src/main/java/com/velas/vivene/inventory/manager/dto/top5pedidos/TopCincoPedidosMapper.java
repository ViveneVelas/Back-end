package com.velas.vivene.inventory.manager.dto.top5pedidos;

import org.springframework.stereotype.Component;

import com.velas.vivene.inventory.manager.entity.view.TopCincoPedidos;

@Component
public class TopCincoPedidosMapper {

    public TopCincoPedidosResponse toDto(TopCincoPedidos topCincoPedidos) {
        if (topCincoPedidos == null) {
            return null;
        }

        TopCincoPedidosResponse dto = new TopCincoPedidosResponse();

        dto.setDataPedido(topCincoPedidos.getDataPedido());
        dto.setNomeCliente(topCincoPedidos.getNomeCliente());
        dto.setQtdVelas(topCincoPedidos.getQtdVelas());

        return dto;
    }
}
