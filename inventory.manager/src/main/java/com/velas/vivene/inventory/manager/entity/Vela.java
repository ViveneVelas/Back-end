package com.velas.vivene.inventory.manager.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name="fk_imagem")
    private Integer fkImagem;

    @OneToMany(mappedBy = "vela", cascade = CascadeType.ALL)
    private List<PedidoVela> pedidoVelas = new ArrayList<>();
}
