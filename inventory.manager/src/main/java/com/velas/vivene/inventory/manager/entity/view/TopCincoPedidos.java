package com.velas.vivene.inventory.manager.entity.view;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "top_cinco_pedidos")
public class TopCincoPedidos {
    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="data_pedido")
    private LocalDate dataPedido;

    @Column(name = "nome_cliente")
    private String nomeCliente;

    @Column(name="quantidade_velas")
    private Integer qtdVelas;
}
