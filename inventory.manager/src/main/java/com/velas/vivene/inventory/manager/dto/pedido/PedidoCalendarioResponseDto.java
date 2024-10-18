package com.velas.vivene.inventory.manager.dto.pedido;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PedidoCalendarioResponseDto {

    private Integer id;
    private LocalDate start;
    private LocalDate end;
    private LocalDate date;
    private String color;
    private Double preco;
    private String titulo;
    private String title;
    private Integer clienteId;
    private String clienteNome;
    private List<VelaPedidoListaDto> listaDeVelas;
}