package com.velas.vivene.inventory.manager.dto.usuario;

import com.velas.vivene.inventory.manager.dto.login.LoginMapper;
import com.velas.vivene.inventory.manager.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final LoginMapper loginMapper;

    public Usuario toEntity(UsuarioRequestDto usuarioRequestDTO) {
        if (usuarioRequestDTO == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequestDTO.getNome());
        usuario.setLogin(loginMapper.toEntity(usuarioRequestDTO.getLogin()));

        return usuario;
    }

    public UsuarioResponseDto toResponseDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioResponseDto responseDTO = new UsuarioResponseDto();
        responseDTO.setId(usuario.getId());
        responseDTO.setNome(usuario.getNome());
        responseDTO.setUltimoAcesso(usuario.getUltimoAcesso());
        responseDTO.setLogin(loginMapper.toResponseDTO(usuario.getLogin()));

        return responseDTO;
    }
}