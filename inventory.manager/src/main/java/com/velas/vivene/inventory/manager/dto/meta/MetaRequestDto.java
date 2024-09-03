package com.velas.vivene.inventory.manager.dto.meta;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MetaRequestDto {
    @NotNull(message = "A data início é obrigatória")
    private LocalDate dataInicio;

    @NotNull(message = "A data final é obrigatória")
    private LocalDate dataFinal;

    @NotNull(message = "A quantidade de vendas é obrigatória")
    private Integer qtdVendas;
}
