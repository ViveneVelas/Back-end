package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Metas Semanais")
public class Meta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "data início")
    private LocalDate dataInicio;

    @Column(name = "data final")
    private LocalDate dataFinal;

    @Column(name = "Quantidade de vendas")
    private Integer qtdVendas;
}
