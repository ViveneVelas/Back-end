package com.velas.vivene.inventory.manager.dto.pedido;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class VelaPedidoListaDto {
    private Integer idVela;
    private String nomeVela;
    private Integer qtdVela;

    public VelaPedidoListaDto(Integer idVela, String nomeVela, Integer qtdVela) {
        this.idVela = idVela;
        this.nomeVela = nomeVela;
        this.qtdVela = qtdVela;
    }
}
