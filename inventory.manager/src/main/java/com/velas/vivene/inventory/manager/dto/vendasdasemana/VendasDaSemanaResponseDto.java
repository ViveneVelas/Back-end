package com.velas.vivene.inventory.manager.dto.vendasdasemana;

import lombok.Data;
import java.time.LocalDate;

@Data
public class VendasDaSemanaResponseDto {
    private Integer vendaId;
    private String metodoPag;
    private LocalDate dataDoPedido; // Data da entrega do pedido
    private String nomeCliente;
    private String telefone;
}
