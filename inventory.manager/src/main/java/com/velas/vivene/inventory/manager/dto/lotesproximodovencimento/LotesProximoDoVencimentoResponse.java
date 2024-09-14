package com.velas.vivene.inventory.manager.dto.lotesproximodovencimento;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LotesProximoDoVencimentoResponse {
    private String nomeVela;
    private LocalDate dataFabricacao;
    private LocalDate dataValidade;
    private Integer qtdDisponivel;
    private Integer diasVencimento;
}
