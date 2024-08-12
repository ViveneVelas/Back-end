package com.velas.vivene.inventory.manager.dto.usuario;

import com.velas.vivene.inventory.manager.dto.login.LoginRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioRequestDto {
    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotNull(message = "Os dados de login são obrigatórios")
    private LoginRequestDto login;
}
