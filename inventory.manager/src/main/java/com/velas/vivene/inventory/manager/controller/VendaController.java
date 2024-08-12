package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.venda.VendaRequestDto;
import com.velas.vivene.inventory.manager.dto.venda.VendaResponseDto;
import com.velas.vivene.inventory.manager.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
@Tag(name = "Venda Controller", description = "APIs para gerenciamento de vendas")
public class VendaController {

    private final VendaService vendaService;

    @Operation(summary = "Cria uma nova venda")
    @PostMapping
    public ResponseEntity<VendaResponseDto> createVenda(@Valid @RequestBody VendaRequestDto vendaRequestDTO) {
        VendaResponseDto responseDTO = vendaService.createVenda(vendaRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza uma venda existente")
    @PutMapping("/{id}")
    public ResponseEntity<VendaResponseDto> updateVenda(@PathVariable Integer id, @Valid @RequestBody VendaRequestDto vendaRequestDTO) {
        VendaResponseDto responseDTO = vendaService.updateVenda(id, vendaRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Deleta uma venda existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Integer id) {
        vendaService.deleteVenda(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Busca todas as vendas")
    @GetMapping
    public ResponseEntity<List<VendaResponseDto>> getAllVendas() {
        List<VendaResponseDto> responseDTOList = vendaService.getAllVendas();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Busca uma venda pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDto> getVendaById(@PathVariable Integer id) {
        VendaResponseDto responseDTO = vendaService.getVendaById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}