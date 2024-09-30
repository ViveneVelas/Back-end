package com.velas.vivene.inventory.manager.dto.pedidovela;
import lombok.Data;

@Data
public class PedidoVelaResponseDto {
    private Integer id;
    private Integer fkVela;
    private Integer fkPedido;
    private Integer qtdVelas;
}