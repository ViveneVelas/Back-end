package com.velas.vivene.inventory.manager.entity.view;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class VelaMaisVendida {

    @Id
    @Column(name = "vela_id")
    private Long id;

    @Column(name = "total_vendido")
    private Integer qtd;

    @Column(name = "vela_nome")
    private String nomeVela;

}

