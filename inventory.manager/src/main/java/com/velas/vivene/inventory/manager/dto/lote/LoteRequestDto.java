package com.velas.vivene.inventory.manager.dto.lote;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class LoteRequestDto {

    @NotNull(message = "O ID da vela é obrigatório")
    private Integer fkVela;

    @NotNull(message = "A quantidade é obrigatória")
    private Integer quantidade;

    @NotNull(message = "A data de fabricação é obrigatória")
    private LocalDate dataFabricacao;

    @NotNull(message = "A data de validade é obrigatória")
    private LocalDate dataValidade;

    @NotNull(message = "A localização é obrigatória")
    private Integer localizacao;
}