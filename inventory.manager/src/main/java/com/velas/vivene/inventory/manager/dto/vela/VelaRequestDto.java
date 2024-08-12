package com.velas.vivene.inventory.manager.dto.vela;

import com.velas.vivene.inventory.manager.commons.Tamanho;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VelaRequestDto {

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "O aroma não pode estar em branco")
    private String aroma;

    @NotNull(message = "O tamanho deve ser especificado")
    private Tamanho tamanho;

    @NotNull(message = "O preço deve ser especificado")
    @Min(value = 0, message = "O preço deve ser maior ou igual a zero")
    private Double preco;
}
