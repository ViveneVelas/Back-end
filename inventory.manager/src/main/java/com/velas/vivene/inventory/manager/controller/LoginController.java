package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.login.LoginRequestDto;
import com.velas.vivene.inventory.manager.dto.login.LoginResponseDto;
import com.velas.vivene.inventory.manager.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logins")
@RequiredArgsConstructor
@Tag(name = "Login Controller", description = "APIs para gerenciamento de logins")
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "Cria um novo login")
    @PostMapping
    public ResponseEntity<LoginResponseDto> createLogin(@Valid @RequestBody LoginRequestDto loginRequestDTO) {
        LoginResponseDto responseDTO = loginService.createLogin(loginRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza um login existente")
    @PutMapping("/{id}")
    public ResponseEntity<LoginResponseDto> updateLogin(@PathVariable Integer id, @Valid @RequestBody LoginRequestDto loginRequestDTO) {
        LoginResponseDto responseDTO = loginService.updateLogin(id, loginRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Deleta um login existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogin(@PathVariable Integer id) {
        loginService.deleteLogin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Busca todos os logins")
    @GetMapping
    public ResponseEntity<List<LoginResponseDto>> getAllLogins() {
        List<LoginResponseDto> responseDTOList = loginService.getAllLogins();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Busca um login pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<LoginResponseDto> getLoginById(@PathVariable Integer id) {
        LoginResponseDto responseDTO = loginService.getLoginById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Realiza o login")
    @PostMapping("/logar")
    public ResponseEntity<LoginResponseDto> getLogin(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto responseDTO = loginService.getLogin(loginRequestDto);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
