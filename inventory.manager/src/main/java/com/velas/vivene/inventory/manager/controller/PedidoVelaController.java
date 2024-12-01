package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaRequestDto;
import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaResponseDto;
import com.velas.vivene.inventory.manager.service.PedidoVelaService;
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
@RequestMapping("/pedidos-velas")
@RequiredArgsConstructor
@Tag(name = "Pedido Vela Controller", description = "APIs para gerenciamento de pedidos velas")
@CrossOrigin(origins = "http://54.236.32.202:3000")
public class PedidoVelaController {

    private final PedidoVelaService pedidoVelaService;

    @PostMapping
    @Operation(summary = "Cria um novo pedido vela", description = """
           # Criar pedido vela
           ---
           Esse endpoint cria um novo pedido vela
           ---
           Nota:
           Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido vela criado com sucesso.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoVelaResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos do pedido vela estão incorretos.",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<PedidoVelaResponseDto> createPedidoVela(@Valid @RequestBody PedidoVelaRequestDto pedidoVelaRequestDto) {
        PedidoVelaResponseDto pedidoVelaResponseDto = pedidoVelaService.createPedidoVela(pedidoVelaRequestDto);
        return new ResponseEntity<>(pedidoVelaResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um pedido vela existente", description = """
           # Atualizar pedido vela
           ---
           Esse endpoint atualiza um pedido vela existente
           ---
           Nota:
           Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido vela atualizado com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoVelaResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos de pedido vela estão incorretos.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "O id informado não existe.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})

    }
    )
    public ResponseEntity<PedidoVelaResponseDto> updatePedidoVela(@PathVariable Integer id, @Valid @RequestBody PedidoVelaRequestDto pedidoVelaRequestDto) {
        PedidoVelaResponseDto pedidoVelaResponseDto = pedidoVelaService.updatePedidoVela(id, pedidoVelaRequestDto);
        return new ResponseEntity<>(pedidoVelaResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um pedido vela existente", description = """
           # Deletar pedido vela
           ---
           Esse endpoint deleta um pedido vela existente
           ---
           Nota:
           O pedido vela deve existir no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido vela deletado com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Pedido vela não encontrado no banco de dados.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<Void> deletePedidoVela(@PathVariable Integer id) {
        pedidoVelaService.deletePedidoVela(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Busca todos os pedidos vela", description = """
           # Busca todos os pedidos vela
           ---
           Esse endpoint busca todos os pedidos vela existentes
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos vela buscados com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoVelaResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhum pedido vela encontrado no banco de dados.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<List<PedidoVelaResponseDto>> getAllPedidoVela() {
        List<PedidoVelaResponseDto> pedidoVelaResponseDtos = pedidoVelaService.getAllPedidoVela();
        return new ResponseEntity<>(pedidoVelaResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca pedido vela pelo ID", description = """
           # Busca pedido vela pelo ID
           ---
           Esse endpoint busca um pedido vela existente pelo ID
           ---
           Nota:
           O ID é obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido vela encontrado com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoVelaResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Pedido vela não encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<PedidoVelaResponseDto> getPedidoVelaById(@PathVariable Integer id) {
        PedidoVelaResponseDto pedidoVelaResponseDto = pedidoVelaService.getPedidoVelaById(id);
        return new ResponseEntity<>(pedidoVelaResponseDto, HttpStatus.OK);
    }

}
