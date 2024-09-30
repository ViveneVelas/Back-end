package com.velas.vivene.inventory.manager.entity.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class UltimaMetaSeisMeses {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "quantidade_vendas")
    private Integer qtdVendas;

}
