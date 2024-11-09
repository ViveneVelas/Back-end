package com.velas.vivene.inventory.manager.dto.pedidovela;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoVelaRequestDto {
    @NotNull
    private Integer velaId;

    @NotNull
    private Integer pedidoId;

    @NotNull
    private Integer quantidade;
}