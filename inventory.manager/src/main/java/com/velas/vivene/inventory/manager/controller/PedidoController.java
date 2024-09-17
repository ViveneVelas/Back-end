package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.pedido.PedidoRequestDto;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoResponseDto;
import com.velas.vivene.inventory.manager.dto.quantidadevendasseismeses.QuantidadeVendasSeisMesesResponse;
import com.velas.vivene.inventory.manager.service.PedidoService;
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
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedido Controller", description = "APIs para gerenciamento de pedidos")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Cria um novo pedido", description = """
           # Criar pedido
           ---
           Esse endpoint cria um novo pedido
           ---
           Nota:
           Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos de pedido estão incorretos.",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<PedidoResponseDto> criarPedido(@Valid @RequestBody PedidoRequestDto pedidoRequest) {
        PedidoResponseDto novoPedido = pedidoService.criarPedido(pedidoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um pedido existente", description = """
           # Atualizar pedido
           ---
           Esse endpoint atualiza um pedido existente
           ---
           Nota:
           Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos de pedido estão incorretos.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<PedidoResponseDto> updatePedido(@PathVariable Integer id, @Valid @RequestBody PedidoRequestDto pedidoRequestDTO) {
        PedidoResponseDto responseDTO = pedidoService.updatePedido(id, pedidoRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/finaliza/{id}")
    @Operation(summary = "Finaliza um pedido existente", description = """
           # Finalizar pedido
           ---
           Esse endpoint finaliza um pedido existente
           ---
           Nota:
           O id é obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido finalizado com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<PedidoResponseDto> finalizaPedido(@PathVariable Integer id) {
        PedidoResponseDto responseDTO = pedidoService.finalizaPedido(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um pedido existente", description = """
           # Deletar pedido
           ---
           Esse endpoint deleta um pedido existente
           ---
           Nota:
           O pedido deve existir no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<Void> deletePedido(@PathVariable Integer id) {
        pedidoService.deletePedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Busca todos os pedidos", description = """
           # Busca todos os pedidos
           ---
           Esse endpoint busca todos os pedidos existentes
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos buscados com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhum pedido encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<PedidoResponseDto>> getAllPedidos() {
        List<PedidoResponseDto> responseDTOList = pedidoService.getAllPedidos();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um pedido pelo ID", description = """
           # Busca pedido pelo id
           ---
           Esse endpoint busca um pedido existente pelo id
           ---
           Nota:
           O id é obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<PedidoResponseDto> getPedidoById(@PathVariable Integer id) {
        PedidoResponseDto responseDTO = pedidoService.getPedidoById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/quantidade-vendas")
    @Operation(summary = "Busca a quantidade de vendas por mês em um período de seis meses", description = """
           # Busca quantidade de vendas
           ---
           Esse endpoint busca a quantidade de vendas por mês em um período de seis meses
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade de vendas encontrada com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = QuantidadeVendasSeisMesesResponse.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhuma venda encontrada.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<QuantidadeVendasSeisMesesResponse>> getQuantidadeVendasSeisMeses() {
        List<QuantidadeVendasSeisMesesResponse> responses = pedidoService.getQuantidadeVendasSeisMeses();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}