package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.usuario.UsuarioRequestDto;
import com.velas.vivene.inventory.manager.dto.usuario.UsuarioResponseDto;
import com.velas.vivene.inventory.manager.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuario Controller", description = "APIs para gerenciamento de usuários")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Cria um novo usuário")
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> createUsuario(@Valid @RequestBody UsuarioRequestDto usuarioRequestDTO) {
        UsuarioResponseDto responseDTO = usuarioService.createUsuario(usuarioRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza um usuário existente")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> updateUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioRequestDto usuarioRequestDTO) {
        UsuarioResponseDto responseDTO = usuarioService.updateUsuario(id, usuarioRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Deleta um usuário existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Busca todos os usuários")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsuarios() {
        List<UsuarioResponseDto> responseDTOList = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Busca um usuário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getUsuarioById(@PathVariable Integer id) {
        UsuarioResponseDto responseDTO = usuarioService.getUsuarioById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Busca um usuário pelo ID de Login (FK)")
    @GetMapping("/fklogin/{id}")
    public ResponseEntity<UsuarioResponseDto> getUsuarioByIdLogin(@RequestParam("loginId") Integer loginId) {
        UsuarioResponseDto responseDTO = usuarioService.getUsuarioByIdLogin(loginId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
