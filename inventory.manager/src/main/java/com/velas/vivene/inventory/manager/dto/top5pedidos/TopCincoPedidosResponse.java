package com.velas.vivene.inventory.manager.dto.top5pedidos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TopCincoPedidosResponse {

    private LocalDate dataPedido;
    private String nomeCliente;
    private Integer qtdVelas;
}
