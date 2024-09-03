package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class LotesProximoDoVencimento {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "Nome da vela")
    private String nomeVela;
    @Column(name = "Data de fabricação")
    private LocalDate dataFabricacao;
    @Column(name = "Data de validade")
    private LocalDate dataValidade;
    @Column(name = "Quantidade disponível")
    private Integer qtdDisponivel;
    @Column(name = "Dias para vencimento")
    private Integer diasVencimento;
}
