package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.pedidolote.PedidoLoteRequestDto;
import com.velas.vivene.inventory.manager.dto.pedidolote.PedidoLoteResponseDto;
import com.velas.vivene.inventory.manager.service.PedidoLoteService;
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
@RequestMapping("/pedidos-lotes")
@RequiredArgsConstructor
@Tag(name = "Pedido Lote Controller", description = "APIs para gerenciamento de pedidos lotes")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoLoteController {

    private final PedidoLoteService pedidoLoteService;

    @PostMapping
    @Operation(summary = "Cria um novo pedido lote", description = """
           # Criar pedido lote
           ---
           Esse endpoint cria um novo pedido lote
           ---
           Nota:
           Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido lote criado com sucesso.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoLoteResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos do pedido lote estão incorretos.",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<PedidoLoteResponseDto> createPedidoLote(@Valid @RequestBody PedidoLoteRequestDto pedidoLoteRequestDto) {
        PedidoLoteResponseDto pedidoLoteResponseDto = pedidoLoteService.createPedidoLote(pedidoLoteRequestDto);
        return new ResponseEntity<>(pedidoLoteResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um pedido lote existente", description = """
           # Atualizar pedido lote
           ---
           Esse endpoint atualiza um pedido lote existente
           ---
           Nota:
           Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido lote atualizado com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoLoteResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos do pedido lote estão incorretos.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<PedidoLoteResponseDto> updatePedidoLote(@PathVariable Integer id, @Valid @RequestBody PedidoLoteRequestDto pedidoLoteRequestDto) {
        PedidoLoteResponseDto pedidoLoteResponseDto = pedidoLoteService.updatePedidoLote(id, pedidoLoteRequestDto);
        return new ResponseEntity<>(pedidoLoteResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um pedido lote existente", description = """
           # Deletar pedido lote
           ---
           Esse endpoint deleta um pedido lote existente
           ---
           Nota:
           O pedido lote deve existir no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido lote deletado com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Pedido lote não encontrado no banco de dados.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<Void> deletePedidoLote(@PathVariable Integer id) {
        pedidoLoteService.deletePedidoLote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Busca todos os pedidos lote", description = """
           # Busca todos os pedidos lote
           ---
           Esse endpoint busca todos os pedidos lote existentes
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos lote buscados com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoLoteResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhum pedido lote encontrado no banco de dados.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<List<PedidoLoteResponseDto>> getAllPedidoLote() {
        List<PedidoLoteResponseDto> pedidoLoteResponseDtos = pedidoLoteService.getAllPedidoLote();
        return new ResponseEntity<>(pedidoLoteResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca pedido lote pelo ID", description = """
           # Busca pedido lote pelo ID
           ---
           Esse endpoint busca um pedido lote existente pelo ID
           ---
           Nota:
           O ID é obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido lote encontrado com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoLoteResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Pedido lote não encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<PedidoLoteResponseDto> getPedidoLoteById(@PathVariable Integer id) {
        PedidoLoteResponseDto pedidoLoteResponseDto = pedidoLoteService.getPedidoLoteById(id);
        return new ResponseEntity<>(pedidoLoteResponseDto, HttpStatus.OK);
    }

}
