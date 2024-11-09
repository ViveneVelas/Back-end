package com.velas.vivene.inventory.manager.dto.pedido;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoRequestDto {

    @NotNull(message = "A data do pedido é obrigatória.")
    private LocalDate dtPedido;

    @NotNull(message = "O preço é obrigatório.")
    private Double preco;

    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;

    @NotBlank(message = "O tipo de entrega é obrigatório.")
    private String tipoEntrega;

    @NotBlank(message = "O status do pedido é obrigatório.")
    private String status;

    @NotNull(message = "O ID do cliente é obrigatório.")
    private Integer clienteId;

    private List<VelaPedidoListaDto> listaVelas;
//    @NotNull(message = "O ID da vela é obrigatório")
//    private Integer velaId;
//
//    @NotNull(message = "A quantidade de velas do pedido é obrigatório")
//    private Integer qtdVelas;
}