package com.velas.vivene.inventory.manager.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Lotes")
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_vela", nullable = false)
    private Vela vela;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "data_fabricacao")
    private LocalDate dataFabricacao;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @Column(name = "localizacao")
    private Integer localizacao;

}
