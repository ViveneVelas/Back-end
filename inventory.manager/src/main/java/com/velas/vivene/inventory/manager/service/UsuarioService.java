package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.usuario.UsuarioMapper;
import com.velas.vivene.inventory.manager.dto.usuario.UsuarioRequestDto;
import com.velas.vivene.inventory.manager.dto.usuario.UsuarioResponseDto;
import com.velas.vivene.inventory.manager.entity.Login;
import com.velas.vivene.inventory.manager.entity.Usuario;
import com.velas.vivene.inventory.manager.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioResponseDto createUsuario(UsuarioRequestDto usuarioRequestDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioRequestDTO);
        usuario.setUltimoAcesso(LocalDate.now());
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuario);
    }

    public UsuarioResponseDto updateUsuario(Integer id, UsuarioRequestDto usuarioRequestDTO) {
        Optional <Usuario> usuario = Optional.ofNullable(usuarioRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("Usuário não encontrado com o id: "+id)
                ));

        usuario.get().setNome(usuarioRequestDTO.getNome());
        usuario.get().setLogin(usuarioMapper.toEntity(usuarioRequestDTO).getLogin());
        usuario.get().setUltimoAcesso(LocalDate.now());

        Usuario usuarioSaved =  usuarioRepository.save(usuario.get());

        return usuarioMapper.toResponseDTO(usuarioSaved);
    }

    public void deleteUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id: " + id));
        usuarioRepository.delete(usuario);
    }

    public List<UsuarioResponseDto> getAllUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDto getUsuarioById(Integer id) {
        Optional <Usuario> usuario = Optional.ofNullable(usuarioRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado com o id: "+id)
        ));
        return usuarioMapper.toResponseDTO(usuario.get());
    }

    public UsuarioResponseDto getUsuarioByIdLogin(Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findByLoginId(id);
//                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado com o email e senha fornecidos."));
        return usuarioMapper.toResponseDTO(usuario.get());
    }
}
