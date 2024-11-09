package com.velas.vivene.inventory.manager.dto.pedido;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    private List<VelaPedidoListaDto> listaDeVelas;
}