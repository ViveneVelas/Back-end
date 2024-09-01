package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.pedidolote.PedidoLoteRequestDto;
import com.velas.vivene.inventory.manager.dto.pedidolote.PedidoLoteResponseDto;
import com.velas.vivene.inventory.manager.service.PedidoLoteService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Cria um novo pedido lote")
    @PostMapping
    public ResponseEntity<PedidoLoteResponseDto> createPedidoLote(@Valid @RequestBody PedidoLoteRequestDto pedidoLoteRequestDto) {
        PedidoLoteResponseDto pedidoLoteResponseDto = pedidoLoteService.createPedidoLote(pedidoLoteRequestDto);
        return new ResponseEntity<>(pedidoLoteResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza um pedido lote existente")
    @PutMapping("/{id}")
    public ResponseEntity<PedidoLoteResponseDto> updatePedidoLote(@PathVariable Integer id, @Valid @RequestBody PedidoLoteRequestDto pedidoLoteRequestDto) {
        PedidoLoteResponseDto pedidoLoteResponseDto = pedidoLoteService.updatePedidoLote(id, pedidoLoteRequestDto);
        return new ResponseEntity<>(pedidoLoteResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "Deleta um pedido lote existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedidoLote(@PathVariable Integer id) {
        pedidoLoteService.deletePedidoLote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Busca todos os pedidos lote")
    @GetMapping
    public ResponseEntity<List<PedidoLoteResponseDto>> getAllPedidoLote() {
        List<PedidoLoteResponseDto> pedidoLoteResponseDtos = pedidoLoteService.getAllPedidoLote();
        return new ResponseEntity<>(pedidoLoteResponseDtos, HttpStatus.OK);
    }

    @Operation(summary = "Busca pedido lote pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoLoteResponseDto> getPedidoLoteById(@PathVariable Integer id) {
        PedidoLoteResponseDto pedidoLoteResponseDto = pedidoLoteService.getPedidoLoteById(id);
        return new ResponseEntity<>(pedidoLoteResponseDto, HttpStatus.OK);
    }

}
