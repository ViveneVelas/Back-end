package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.cliente.ClienteRequestDto;
import com.velas.vivene.inventory.manager.dto.cliente.ClienteResponseDto;
import com.velas.vivene.inventory.manager.dto.clientesmaiscompras.ClienteMaisComprasResponse;
import com.velas.vivene.inventory.manager.dto.lote.LoteResponseDto;
import com.velas.vivene.inventory.manager.service.ClienteService;
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
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Cliente Controller", description = "APIs para gerenciamento de clientes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Criar cliente", description = """
           # Criar cliente
           ---
           Esse endpoint busca clientes por id
           ---
           Nota:
           - Todos os campos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clientes criado com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Dados do cliente incorretos.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<ClienteResponseDto> createCliente(@Valid @RequestBody ClienteRequestDto clienteRequestDTO) {
        ClienteResponseDto responseDTO = clienteService.createCliente(clienteRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza cliente por id", description = """
           # Atualizar cliente por id
           ---
           Esse endpoint atualiza clientes por id
           ---
           Nota:
           - Id Obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente Alterado com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<ClienteResponseDto> updateCliente(@PathVariable Integer id, @Valid @RequestBody ClienteRequestDto clienteRequestDTO) {
        ClienteResponseDto responseDTO = clienteService.updateCliente(id, clienteRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente por id", description = """
           # Deletar cliente por id
           ---
           Esse endpoint deleta um clientes por id
           ---
           Nota:
           - Id Obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Buscar todos os cliente", description = """
           # Buscar todos os clientes
           ---
           Esse endpoint busca clientes
           ---
           Nota:
           - N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Clientes não encontrado (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<ClienteResponseDto>> getAllClientes() {
        List<ClienteResponseDto> responseDTOList = clienteService.getAllClientes();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por id", description = """
           # Buscar cliente por id
           ---
           Esse endpoint busca clientes por id
           ---
           Nota:
           - Id Obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Clientes não encontrado (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<ClienteResponseDto> getClienteById(@PathVariable Integer id) {
        ClienteResponseDto responseDTO = clienteService.getClienteById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/clientes-mais-compras")
    @Operation(summary = "Buscar clientes com mais compras", description = """
           # Busca clientes com mais compras
           ---
           Esse endpoint busca clientes com mais compras
           ---
           Nota:
           - N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteMaisComprasResponse.class))})})
    public ResponseEntity<List<ClienteMaisComprasResponse>> getClienteMaisCompras() {
        List<ClienteMaisComprasResponse> responseDTO = clienteService.getClienteMaisCompras();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}