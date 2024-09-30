package com.velas.vivene.inventory.manager.entity.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class VendasDaSemana {
    @Id
    @Column(name = "id")
    private Integer vendaId;

    @Column(name = "metodo_de_pagamento")
    private String metodoPag;

    @Column(name = "data_do_pedido")
    private LocalDate dataDoPedido; // Data da entrega do pedido

    @Column(name = "nome_do_cliente")
    private String nomeCliente;

    @Column(name = "telefone_do_cliente")
    private String telefone;
}
