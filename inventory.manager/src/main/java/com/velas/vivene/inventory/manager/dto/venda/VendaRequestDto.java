package com.velas.vivene.inventory.manager.dto.venda;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaRequestDto {

    @NotNull(message = "O ID do pedido é obrigatório.")
    private Integer pedidoId;

    @NotBlank(message = "O método de pagamento é obrigatório.")
    private String metodoPag;
}