package com.velas.vivene.inventory.manager.entity;

import com.velas.vivene.inventory.manager.commons.Estado;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "Pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_do_pedido")
    private LocalDate dtPedido;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "tipoEntrega")
    private String tipoEntrega;

    @Column(name = "status_do_pedid")
    private String status;

    @OneToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;
}
