package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.usuario.UsuarioRequestDto;
import com.velas.vivene.inventory.manager.dto.usuario.UsuarioResponseDto;
import com.velas.vivene.inventory.manager.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@CrossOrigin(origins = "http://3.87.199.254:3000")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Cria um novo usuário", description = """
           # Criar usuário
           ---
           Esse endpoint cria um novo usuário no sistema.
           ---
           Nota:
           Todos os atributos são obrigatórios
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso (Created).",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados fornecidos.",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> createUsuario(@Valid @RequestBody UsuarioRequestDto usuarioRequestDTO) {
        UsuarioResponseDto responseDTO = usuarioService.createUsuario(usuarioRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza um usuário existente", description = """
           # Atualizar usuário
           ---
           Esse endpoint atualiza os dados de um usuário existente no sistema.
           ---
           Nota:
           Todos os atributos são obrigatórios
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos de usuário estão incorretos.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "O id informado não existe.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> updateUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioRequestDto usuarioRequestDTO) {
        UsuarioResponseDto responseDTO = usuarioService.updateUsuario(id, usuarioRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Deleta um usuário existente", description = """
           # Deletar usuário
           ---
           Esse endpoint remove um usuário do sistema.
           ---
           Nota:
           O usuário deve existir no banco de dados
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso (No Content)."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Busca todos os usuários", description = """
           # Buscar todos os usuários
           ---
           Esse endpoint retorna uma lista de todos os usuários cadastrados no sistema.
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso (OK).",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado.",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsuarios() {
        List<UsuarioResponseDto> responseDTOList = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Busca um usuário pelo ID", description = """
           # Buscar usuário por ID
           ---
           Esse endpoint retorna os dados de um usuário a partir do seu ID.
           ---
           Nota:
           O ID é obrigatório
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso (OK).",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getUsuarioById(@PathVariable Integer id) {
        UsuarioResponseDto responseDTO = usuarioService.getUsuarioById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Busca um usuário pelo ID de Login (FK)", description = """
           # Buscar usuário por ID de Login
           ---
           Esse endpoint retorna os dados de um usuário a partir do ID de Login (FK)
           ---
           Nota:
           O id do login é obrigatório
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso (OK).",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/fklogin/{id}")
    public ResponseEntity<UsuarioResponseDto> getUsuarioByIdLogin(@RequestParam("loginId") Integer loginId) {
        UsuarioResponseDto responseDTO = usuarioService.getUsuarioByIdLogin(loginId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
