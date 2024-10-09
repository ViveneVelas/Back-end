package com.velas.vivene.inventory.manager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.velas.vivene.inventory.manager.commons.exceptions.CustomDataIntegrityViolationException;
import com.velas.vivene.inventory.manager.commons.exceptions.NoContentException;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.commons.exceptions.UnexpectedServerErrorException;
import com.velas.vivene.inventory.manager.dto.login.LoginMapper;
import com.velas.vivene.inventory.manager.dto.login.LoginRequestDto;
import com.velas.vivene.inventory.manager.dto.login.LoginResponseDto;
import com.velas.vivene.inventory.manager.entity.Login;
import com.velas.vivene.inventory.manager.repository.LoginRepository;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final LoginMapper loginMapper;

    public LoginResponseDto createLogin(LoginRequestDto loginRequestDTO) {
        if (loginRequestDTO == null) {
            throw new ValidationException("Os dados do login são obrigatórios.");
        }
        try {
            Login login = loginMapper.toEntity(loginRequestDTO);
            login = loginRepository.save(login);
            return loginMapper.toResponseDTO(login);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao salvar o login.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao criar login " + ex);
        }
    }

    public LoginResponseDto updateLogin(Integer id, LoginRequestDto loginRequestDTO) {
        Login login = loginRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Login não encontrado com o id: " + id));

        if (loginRequestDTO == null) {
            throw new ValidationException("Os dados do login são obrigatórios.");
        }

        try {
            login.setEmail(loginRequestDTO.getEmail());
            login.setSenha(loginRequestDTO.getSenha());
            login = loginRepository.save(login);
            return loginMapper.toResponseDTO(login);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao atualizar o login.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao atualizar login " + ex);
        }
    }

    public void deleteLogin(Integer id) {
        Login login = loginRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Login não encontrado com o id: " + id));
        loginRepository.delete(login);
    }

    public List<LoginResponseDto> getAllLogins() {
        List<LoginResponseDto> logins = loginRepository.findAll()
                .stream()
                .map(loginMapper::toResponseDTO)
                .collect(Collectors.toList());

        if (logins.isEmpty()) {
            throw new NoContentException("Não existe nenhum login no banco de dados");
        }

        return logins;
    }

    public LoginResponseDto getLoginById(Integer id) {
        Login login = loginRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Login não encontrado com o id: " + id));
        return loginMapper.toResponseDTO(login);
    }

    public LoginResponseDto getLogin(LoginRequestDto loginRequestDto) {
        Login login = loginRepository.findByEmailAndSenha(loginRequestDto.getEmail(), loginRequestDto.getSenha())
                .orElseThrow(() -> new ResourceNotFoundException("Login não encontrado com o email e senha fornecidos."));
        return loginMapper.toResponseDTO(login);
    }
}
