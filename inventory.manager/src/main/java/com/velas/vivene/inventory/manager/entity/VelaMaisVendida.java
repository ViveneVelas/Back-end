package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class VelaMaisVendida {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "Total_Vendido")
    private Integer qtd;

    @Column(name = "Nome_da_Vela")
    private String nomeVela;

}

