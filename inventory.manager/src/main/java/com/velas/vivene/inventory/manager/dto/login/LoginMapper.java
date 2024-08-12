package com.velas.vivene.inventory.manager.dto.login;

import com.velas.vivene.inventory.manager.entity.Login;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {

    public Login toEntity(LoginRequestDto loginRequestDTO) {
        if (loginRequestDTO == null) {
            return null;
        }

        Login login = new Login();
        login.setEmail(loginRequestDTO.getEmail());
        login.setSenha(loginRequestDTO.getSenha());

        return login;
    }

    public LoginResponseDto toResponseDTO(Login login) {
        if (login == null) {
            return null;
        }

        LoginResponseDto responseDTO = new LoginResponseDto();
        responseDTO.setId(login.getId());
        responseDTO.setEmail(login.getEmail());

        return responseDTO;
    }
}