package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "velas")
public class Vela {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 45)
    private String nome;

    @Column(name = "tamanho", length = 1)
    private String tamanho;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco")
    private Double preco;

    @OneToMany(mappedBy = "vela", cascade = CascadeType.ALL)
    private List<PedidoVela> pedidoVelas = new ArrayList<>();
}
