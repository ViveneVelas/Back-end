package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.login.LoginMapper;
import com.velas.vivene.inventory.manager.dto.login.LoginRequestDto;
import com.velas.vivene.inventory.manager.dto.login.LoginResponseDto;
import com.velas.vivene.inventory.manager.entity.Login;
import com.velas.vivene.inventory.manager.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final LoginMapper loginMapper;

    public LoginResponseDto createLogin(LoginRequestDto loginRequestDTO) {
        Login login = loginMapper.toEntity(loginRequestDTO);
        login = loginRepository.save(login);
        return loginMapper.toResponseDTO(login);
    }

    public LoginResponseDto updateLogin(Integer id, LoginRequestDto loginRequestDTO) {
        Login login = loginRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Login não encontrado com o id: " + id));

        login.setEmail(loginRequestDTO.getEmail());
        login.setSenha(loginRequestDTO.getSenha());

        login = loginRepository.save(login);
        return loginMapper.toResponseDTO(login);
    }

    public void deleteLogin(Integer id) {
        Login login = loginRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Login não encontrado com o id: " + id));
        loginRepository.delete(login);
    }

    public List<LoginResponseDto> getAllLogins() {
        return loginRepository.findAll()
                .stream()
                .map(loginMapper::toResponseDTO)
                .collect(Collectors.toList());
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