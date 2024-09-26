package com.velas.vivene.inventory.manager.dto.pedido;


import com.velas.vivene.inventory.manager.dto.pedidolote.PedidoLoteResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PedidoResponseDto {

    private Integer id;
    private LocalDate dtPedido;
    private Double preco;
    private String descricao;
    private String tipoEntrega;
    private String status;
    private Integer clienteId;
    private String clienteNome;
    private PedidoLoteResponseDto pedidoLoteResponseDto;
}