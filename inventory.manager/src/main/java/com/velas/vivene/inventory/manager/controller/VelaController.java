package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.vela.VelaRequestDto;
import com.velas.vivene.inventory.manager.dto.vela.VelaResponseDto;
import com.velas.vivene.inventory.manager.dto.velamaisvendida.VelaMaisVendidaResponse;
import com.velas.vivene.inventory.manager.service.VelaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/velas")
@RequiredArgsConstructor
@Tag(name = "Vela Controller", description = "APIs para gerenciamento de velas")
@CrossOrigin(origins = "http://localhost:3000")
public class VelaController {

    private final VelaService velaService;

    @Operation(summary = "Cria uma nova vela")
    @PostMapping
    public ResponseEntity<VelaResponseDto> createVela(@Valid @RequestBody VelaRequestDto velaRequestDTO) {
        VelaResponseDto responseDTO = velaService.createVela(velaRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza uma vela existente")
    @PutMapping("/{id}")
    public ResponseEntity<VelaResponseDto> updateVela(@PathVariable Integer id, @Valid @RequestBody VelaRequestDto velaRequestDTO) {
        VelaResponseDto responseDTO = velaService.updateVela(id, velaRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Deleta uma vela existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVela(@PathVariable Integer id) {
        velaService.deleteVela(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Busca todas as velas")
    @GetMapping
    public ResponseEntity<List<VelaResponseDto>> getAllVelas() {
        List<VelaResponseDto> responseDTOList = velaService.getAllVelas();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Busca uma vela pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<VelaResponseDto> getVelaById(@PathVariable Integer id) {
        VelaResponseDto responseDTO = velaService.getVelaById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Buscar vela mais vendida")
    @GetMapping("/maisvendida")
    public ResponseEntity<List<VelaMaisVendidaResponse>> getVelaVendida() {
        List<VelaMaisVendidaResponse> responseDTO = velaService.getVelaVendida();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}