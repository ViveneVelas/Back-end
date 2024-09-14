package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TopCincoVelas {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "Nome_da_vela")
    private String nomeVela;

    @Column(name = "Total_Vendido")
    private Integer totalVendido;
}
