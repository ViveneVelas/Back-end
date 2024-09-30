package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "metas")
public class Meta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_final")
    private LocalDate dataFinal;

    @Column(name = "qtd_vendas")
    private Integer qtdVendas;
}
