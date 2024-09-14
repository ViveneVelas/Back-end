package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Pedido_lote")
public class PedidoLote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "fk_lote", nullable = false)
    private Lote lote;

    @Column(name = "quantidade")
    private Integer quantidade;

}