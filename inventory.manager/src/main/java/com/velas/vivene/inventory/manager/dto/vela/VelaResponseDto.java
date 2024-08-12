package com.velas.vivene.inventory.manager.dto.vela;

import com.velas.vivene.inventory.manager.commons.Tamanho;
import lombok.Data;

@Data
public class VelaResponseDto {

    private Integer id;
    private String nome;
    private String aroma;
    private Tamanho tamanho;
    private Double preco;
}
