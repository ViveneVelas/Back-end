package com.velas.vivene.inventory.manager.entity.view;

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
    @Column(name = "vela_id")
    private Long id;

    @Column(name = "vela_nome")
    private String nomeVela;

    @Column(name = "total_vendido")
    private Integer totalVendido;
}
