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
    @Column(name = "nome_da_vela")
    private String nomeVela;
    @Column(name = "dt_fabricacao")
    private LocalDate dataFabricacao;
    @Column(name = "dt_validade")
    private LocalDate dataValidade;
    @Column(name = "qtd_disponivel")
    private Integer qtdDisponivel;
    @Column(name = "qtd_vencimento")
    private Integer diasVencimento;
}
