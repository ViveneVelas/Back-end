package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ClientesMaisCompras {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "Nome do cliente")
    private String nomeCliente;
    @Column(name = "Número de pedidos")
    private Integer numPedidos;
}
