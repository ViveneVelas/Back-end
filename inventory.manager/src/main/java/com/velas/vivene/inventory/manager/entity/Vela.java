package com.velas.vivene.inventory.manager.entity;

import com.velas.vivene.inventory.manager.commons.Tamanho;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "Velas")
public class Vela {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tamanho")
    private String tamanho;

    @Column(name = "preco")
    private Double preco;

}
