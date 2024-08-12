package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.pedido.PedidoRequestDto;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoResponseDto;
import com.velas.vivene.inventory.manager.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
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
public class PedidoController {

    private final PedidoService pedidoService;

    @Operation(summary = "Cria um novo pedido")
    @PostMapping
    public ResponseEntity<PedidoResponseDto> createPedido(@Valid @RequestBody PedidoRequestDto pedidoRequestDTO) {
        PedidoResponseDto responseDTO = pedidoService.createPedido(pedidoRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza um pedido existente")
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> updatePedido(@PathVariable Integer id, @Valid @RequestBody PedidoRequestDto pedidoRequestDTO) {
        PedidoResponseDto responseDTO = pedidoService.updatePedido(id, pedidoRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Finaliza um pedido existente")
    @PutMapping("/finaliza/{id}")
    public ResponseEntity<PedidoResponseDto> finalizaPedido(@PathVariable Integer id) {
        PedidoResponseDto responseDTO = pedidoService.finalizaPedido(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Deleta um pedido existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Integer id) {
        pedidoService.deletePedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Busca todos os pedidos")
    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> getAllPedidos() {
        List<PedidoResponseDto> responseDTOList = pedidoService.getAllPedidos();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Busca um pedido pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> getPedidoById(@PathVariable Integer id) {
        PedidoResponseDto responseDTO = pedidoService.getPedidoById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}