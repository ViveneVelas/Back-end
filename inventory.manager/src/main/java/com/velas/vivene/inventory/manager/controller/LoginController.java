package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.login.LoginRequestDto;
import com.velas.vivene.inventory.manager.dto.login.LoginResponseDto;
import com.velas.vivene.inventory.manager.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    @Operation(summary = "Cria um novo Login", description = """
          # Cria Login
          ---
          Esse endpoint é responsavel por criar um novo login para usar a plataforma.
          
          notes:
          - Todos os campos são requiridos;
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Login Criado com Sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Algum campo está incorreto.",
                    content = { @Content(schema = @Schema())})}
    )
    public ResponseEntity<LoginResponseDto> createLogin(@Valid @RequestBody LoginRequestDto loginRequestDTO) {
        LoginResponseDto responseDTO = loginService.createLogin(loginRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um Login", description = """
          # Atualiza Login
          ---
          Esse endpoint é responsavel por atualizar os dados de login.
          
          notes:
          - O id do login é obrigatório;
          - O login precisa existir no banco de dados;
          - Dados não passados, não serão alterados.
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login Alterado com Sucesso.",
                    content = { @Content(schema = @Schema(implementation = LoginResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Algum campo está incorreto.",
                    content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Login não encontrado.",
                    content = { @Content(schema = @Schema())})
    }
    )
    public ResponseEntity<LoginResponseDto> updateLogin(@PathVariable Integer id, @Valid @RequestBody LoginRequestDto loginRequestDTO) {
        LoginResponseDto responseDTO = loginService.updateLogin(id, loginRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Deleta um Login", description = """
            # Deleta Login
            ---
            Esse endpoint é responsavel por deletar um Login.
                      
            notes:
            - O id do login é obrigatório;
            - O login precisa existir no banco de dados;
            - O Login precisa estar associado a um usuário;
            - Uma vez deletado não é possivel buscar de volta.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Login deletado com sucesso.",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Login não encontrado.",
                    content = {@Content(schema = @Schema())})
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogin(@PathVariable Integer id) {
        loginService.deleteLogin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Busca Todos os Logins", description = """
          # Busca Logins
          ---
          Esse endpoint é responsavel por buscar todos os logins.
          
          notes:
          - N/A
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login encontrados com Sucesso.",
                    content = { @Content(schema = @Schema(implementation = LoginResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Não existe login no banco de dados.",
                    content = { @Content(schema = @Schema())})
    }
    )
    public ResponseEntity<List<LoginResponseDto>> getAllLogins() {
        List<LoginResponseDto> responseDTOList = loginService.getAllLogins();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um login por ID", description = """
          # Busca um Login
          ---
          Esse endpoint é responsavel por buscar um login.
          
          notes:
          - O id do login é obrigatório;
          - O login precisa existir no banco de dados.
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login Encontrado com sucesso.",
                    content = { @Content(schema = @Schema(implementation = LoginResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Login não encontrado.",
                    content = { @Content(schema = @Schema())})
    }
    )
    public ResponseEntity<LoginResponseDto> getLoginById(@PathVariable Integer id) {
        LoginResponseDto responseDTO = loginService.getLoginById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/logar")
    @Operation(summary = "Realizar Login", description = """
          # Realiza login
          ---
          Esse endpoint é responsavel por realizar o login na aplicação, quando passado um email e senha válido.
          
          notes:
          - Todos os campos são obrigatórios.
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login autenticado com sucesso.",
                    content = { @Content(schema = @Schema(implementation = LoginResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Login inválido.",
                    content = { @Content(schema = @Schema())})
    }
    )
    public ResponseEntity<LoginResponseDto> getLogin(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto responseDTO = loginService.getLogin(loginRequestDto);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
