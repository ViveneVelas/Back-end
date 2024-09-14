package com.velas.vivene.inventory.manager.dto.pedidolote;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoLoteRequestDto {
    @NotNull
    private Integer loteId;

    @NotNull
    private Integer pedidoId;

    @NotNull
    private Integer quantidade;
}