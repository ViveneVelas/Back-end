package com.velas.vivene.inventory.manager.dto.imagem;

import lombok.Data;

@Data
public class ImagemResponseDto {

    private Integer id;
    private String referencia;
    private byte[] imagem;
}
