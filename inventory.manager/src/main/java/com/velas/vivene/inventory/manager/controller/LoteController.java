package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.lote.LoteRequestDto;
import com.velas.vivene.inventory.manager.dto.lote.LoteResponseDto;
import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.service.LoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lotes")
@RequiredArgsConstructor
@Tag(name = "Lote Controller", description = "API para gerenciamento de lotes")
public class LoteController {

    private final LoteService loteService;

    @Operation(summary = "Cria um novo lote")
    @PostMapping
    public ResponseEntity<LoteResponseDto> createLote(@Valid @RequestBody LoteRequestDto loteRequestDto) {
        LoteResponseDto loteResponseDto = loteService.criarLote(loteRequestDto);
        return new ResponseEntity<>(loteResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza um lote")
    @PutMapping("/{id}")
    public ResponseEntity<LoteResponseDto> updateLote(@PathVariable Integer id,@Valid @RequestBody LoteRequestDto loteRequestDto) {
        LoteResponseDto loteResponseDto = loteService.updateLote(id, loteRequestDto);
        return new ResponseEntity<>(loteResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "Deleta um lote existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLote(@PathVariable Integer id) {
        loteService.excluirLote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Busca todos os lotes")
    @GetMapping
    public ResponseEntity<List<LoteResponseDto>> getAllLotes() {
        List<LoteResponseDto> responseDtoList = loteService.listarLotes();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @Operation(summary = "Busca um lote por ID")
    @GetMapping("/{id}")
    public ResponseEntity<LoteResponseDto> getLoteById(@PathVariable Integer id) {
        LoteResponseDto loteResponseDto = loteService.obterLotePorId(id);
        return new ResponseEntity<>(loteResponseDto, HttpStatus.OK);
    }
}




