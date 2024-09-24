package com.velas.vivene.inventory.manager.dto.pedidolote;
import lombok.Data;

@Data
public class PedidoLoteResponseDto {
    private Integer id;
    private Integer fkLote;
    private Integer fkPedido;
    private Integer qtdVelas;
}