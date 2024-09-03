package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.meta.MetaMapper;
import com.velas.vivene.inventory.manager.dto.meta.MetaRequestDto;
import com.velas.vivene.inventory.manager.dto.meta.MetaResponseDto;
import com.velas.vivene.inventory.manager.service.MetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metas")
@RequiredArgsConstructor
@Tag(name = "Meta Controller", description = "APIs para gerenciamento de metas")
@CrossOrigin(origins = "http://localhost:3000")
public class MetaController {

    private final MetaService metaService;

    @Operation(summary = "Cria uma nova meta")
    @PostMapping
    public ResponseEntity<MetaResponseDto> createMeta(@Valid @RequestBody MetaRequestDto metaRequestDto) {
        MetaResponseDto metaResponseDto = metaService.createMeta(metaRequestDto);
        return new ResponseEntity<>(metaResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza uma meta existente")
    @PutMapping("/{id}")
    public ResponseEntity<MetaResponseDto> updateMeta(@PathVariable Integer id, @Valid @RequestBody MetaRequestDto metaRequestDto) {
        MetaResponseDto metaResponseDto = metaService.updateMeta(id, metaRequestDto);
        return new ResponseEntity<>(metaResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "Deleta uma meta existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeta(@PathVariable Integer id) {
        metaService.deletarMeta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Busca todas as metas")
    @GetMapping
    public ResponseEntity<List<MetaResponseDto>> getAllMetas() {
        List<MetaResponseDto> metaResponseDtos = metaService.getAllMetas();
        return new ResponseEntity<>(metaResponseDtos, HttpStatus.OK);
    }

    @Operation(summary = "Busca uma meta pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<MetaResponseDto> getMetaById(@PathVariable Integer id) {
        MetaResponseDto metaResponseDto = metaService.getMetaById(id);
        return new ResponseEntity<>(metaResponseDto, HttpStatus.OK);
    }
}
