package com.velas.vivene.inventory.manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.velas.vivene.inventory.manager.commons.exceptions.CustomDataIntegrityViolationException;
import com.velas.vivene.inventory.manager.commons.exceptions.NoContentException;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.commons.exceptions.UnexpectedServerErrorException;
import com.velas.vivene.inventory.manager.dto.usuario.UsuarioMapper;
import com.velas.vivene.inventory.manager.dto.usuario.UsuarioRequestDto;
import com.velas.vivene.inventory.manager.dto.usuario.UsuarioResponseDto;
import com.velas.vivene.inventory.manager.entity.Usuario;
import com.velas.vivene.inventory.manager.repository.UsuarioRepository;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioResponseDto createUsuario(UsuarioRequestDto usuarioRequestDTO) {
        if (usuarioRequestDTO == null) {
            throw new ValidationException("Os dados do usuário são obrigatórios.");
        }

        try {
            Usuario usuario = usuarioMapper.toEntity(usuarioRequestDTO);
            usuario.setUltimoAcesso(LocalDate.now());
            usuario = usuarioRepository.save(usuario);
            return usuarioMapper.toResponseDTO(usuario);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao salvar o usuário.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao criar usuário " + ex);
        }
    }

    public UsuarioResponseDto updateUsuario(Integer id, UsuarioRequestDto usuarioRequestDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id: " + id));

        if (usuarioRequestDTO == null) {
            throw new ValidationException("Os dados do usuário são obrigatórios.");
        }

        try {
            usuario.setNome(usuarioRequestDTO.getNome());
            usuario.setLogin(usuarioMapper.toEntity(usuarioRequestDTO).getLogin());
            usuario.setUltimoAcesso(LocalDate.now());
            Usuario usuarioSaved = usuarioRepository.save(usuario);

            return usuarioMapper.toResponseDTO(usuarioSaved);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao atualizar o usuário.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao atualizar usuário " + ex);
        }
    }

    public void deleteUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id: " + id));
        usuarioRepository.delete(usuario);
    }

    public List<UsuarioResponseDto> getAllUsuarios() {
        List<UsuarioResponseDto> usuarios = usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
        
        if (usuarios.isEmpty()) {
            throw new NoContentException("Não existe nenhum usuário no banco de dados");
        }

        return usuarios;
    }

    public UsuarioResponseDto getUsuarioById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id: " + id));
        return usuarioMapper.toResponseDTO(usuario);
    }

    public UsuarioResponseDto getUsuarioByIdLogin(Integer id) {
        Usuario usuario = usuarioRepository.findByLoginId(id)
        .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id do login: " + id));
        return usuarioMapper.toResponseDTO(usuario);
    }
}
