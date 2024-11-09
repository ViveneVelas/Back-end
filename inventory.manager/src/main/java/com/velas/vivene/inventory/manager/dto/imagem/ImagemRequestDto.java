package com.velas.vivene.inventory.manager.dto.imagem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ImagemRequestDto {

    @NotBlank(message="A referência é obrigatória")
    private String referencia;

    @NotNull(message= "A imagem é obrigatoria")
    private byte[] imagem;

}
