package com.velas.vivene.inventory.manager.dto.quantidadevendasseismeses;

import lombok.Data;
import java.time.LocalDate;

@Data
public class QuantidadeVendasSeisMesesResponse {
    private LocalDate mesAno;

    private Integer qtdPedidosConcluidos;
}
