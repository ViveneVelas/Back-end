package com.velas.vivene.inventory.manager.entity.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class QuantidadeVendasSeisMeses {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "quantidade_pedidos_concluidos")
    private Integer qtdPedidosConcluidos;

}
