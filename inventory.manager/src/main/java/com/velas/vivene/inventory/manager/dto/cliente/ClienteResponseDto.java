package com.velas.vivene.inventory.manager.dto.cliente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponseDto {
    private Integer id;
    private String nome;
    private String telefone;
    private Integer qtdPedidos;
}
