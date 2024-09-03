package com.velas.vivene.inventory.manager.dto.meta;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MetaResponseDto {
    private Integer id;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private Integer qtdVendas;
}
