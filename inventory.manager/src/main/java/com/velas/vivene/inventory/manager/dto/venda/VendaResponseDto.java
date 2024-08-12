package com.velas.vivene.inventory.manager.dto.venda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaResponseDto {

    private Integer id;
    private Integer pedidoId;
    private String metodoPag;
    private Double precoTotal;
    private String statusPedido;
}