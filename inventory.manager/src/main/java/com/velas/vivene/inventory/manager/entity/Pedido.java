package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "tipo_entrega", length = 45)
    private String tipoEntrega;

    @Column(name = "status_do_pedido", length = 45)
    private String status;

    @OneToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE)
    private List<PedidoVela> pedidoVelas = new ArrayList<>();
}
