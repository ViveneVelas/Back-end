package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Top5VelasMaisVendidas {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "Nome da vela")
    private String nomeVela;

    @Column(name = "Total vendido")
    private Integer totalVendido;
}
