package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.meta.MetaRequestDto;
import com.velas.vivene.inventory.manager.dto.meta.MetaResponseDto;
import com.velas.vivene.inventory.manager.dto.ultimametaseismeses.UltimaMetaSeisMesesResponse;
import com.velas.vivene.inventory.manager.service.MetaService;
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
@RequestMapping("/metas")
@RequiredArgsConstructor
@Tag(name = "Meta Controller", description = "APIs para gerenciamento de metas")
@CrossOrigin(origins = "http://18.212.185.46:3000")
public class MetaController {

    private final MetaService metaService;

    @PostMapping
    @Operation(summary = "Cria uma nova meta", description = """
            # Criar meta
            ---
            Esse endpoint cria uma nova meta
            ---
            Nota:
            Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Meta criada com sucesso.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MetaResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos da meta estão incorretos.",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<MetaResponseDto> createMeta(@Valid @RequestBody MetaRequestDto metaRequestDto) {
        MetaResponseDto metaResponseDto = metaService.createMeta(metaRequestDto);
        return new ResponseEntity<>(metaResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma meta existente", description = """
            # Atualizar meta
            ---
            Esse endpoint atualiza uma meta existente
            ---
            Nota:
            Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meta atualizada com sucesso.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MetaResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos da meta estão incorretos.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada.",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<MetaResponseDto> updateMeta(@PathVariable Integer id, @Valid @RequestBody MetaRequestDto metaRequestDto) {
        MetaResponseDto metaResponseDto = metaService.updateMeta(id, metaRequestDto);
        return new ResponseEntity<>(metaResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma meta existente", description = """
            # Deletar meta
            ---
            Esse endpoint deleta uma meta existente
            ---
            Nota:
            A meta deve existir no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Meta deletada com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada.",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<Void> deleteMeta(@PathVariable Integer id) {
        metaService.deletarMeta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Busca todas as metas", description = """
            # Busca todas as metas
            ---
            Esse endpoint busca todas as metas existentes
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Metas buscadas com sucesso.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MetaResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhuma meta encontrada no banco de dados.",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<List<MetaResponseDto>> getAllMetas() {
        List<MetaResponseDto> metaResponseDtos = metaService.getAllMetas();
        return new ResponseEntity<>(metaResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma meta pelo ID", description = """
            # Busca meta pelo ID
            ---
            Esse endpoint busca uma meta existente pelo ID
            ---
            Nota:
            O ID é obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meta encontrada com sucesso.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MetaResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada.",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<MetaResponseDto> getMetaById(@PathVariable Integer id) {
        MetaResponseDto metaResponseDto = metaService.getMetaById(id);
        return new ResponseEntity<>(metaResponseDto, HttpStatus.OK);
    }

    @GetMapping("/ultima-meta")
    @Operation(summary = "Busca a última meta de cada mês, num período de seis meses", description = """
            # Busca última meta de cada mês
            ---
            Esse endpoint busca a última meta de cada mês nos últimos seis meses
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Metas buscadas com sucesso.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UltimaMetaSeisMesesResponse.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhuma meta encontrada no período especificado.",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<List<UltimaMetaSeisMesesResponse>> getUltimaMetaSeisMeses() {
        List<UltimaMetaSeisMesesResponse> responses = metaService.getUltimaMetaSeisMeses();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
