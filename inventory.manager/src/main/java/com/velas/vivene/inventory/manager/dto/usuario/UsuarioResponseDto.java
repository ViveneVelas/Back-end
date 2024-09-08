package com.velas.vivene.inventory.manager.dto.usuario;

import com.velas.vivene.inventory.manager.dto.login.LoginResponseDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioResponseDto {

    private Integer id;
    private String nome;
    private String telefone;
    private LocalDate ultimoAcesso;
    private LoginResponseDto login;
}