package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 45)
    private String nome;

    @Column(name = "telefone", length = 11)
    private String telefone;

    @Column(name = "qtd_pedidos")
    private Integer qtdPedidos;

}

