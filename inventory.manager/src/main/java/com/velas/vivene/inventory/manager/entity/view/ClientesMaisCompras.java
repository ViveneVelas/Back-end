package com.velas.vivene.inventory.manager.entity.view;

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
    @Column(name = "nome_do_cliente")
    private String nomeCliente;
    @Column(name = "numero_de_pedidos")
    private Integer numPedidos;
}