package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.lote.LoteRequestDto;
import com.velas.vivene.inventory.manager.dto.lote.LoteResponseDto;
import com.velas.vivene.inventory.manager.dto.lotesproximodovencimento.LotesProximoDoVencimentoResponse;
import com.velas.vivene.inventory.manager.dto.venda.VendaResponseDto;
import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.service.LoteService;
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
@RequestMapping("/lotes")
@RequiredArgsConstructor
@Tag(name = "Lote Controller", description = "API para gerenciamento de lotes")
@CrossOrigin(origins = "http://localhost:3000")
public class LoteController {

    private final LoteService loteService;

    @PostMapping
    @Operation(summary = "Cria um lote", description = """
           # Criar lote
           ---
           Esse endpoint cria os dados de um lote.
           ---
           Nota:
           - Todos os campos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lote criado com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoteResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos de lote estão incorretos",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<LoteResponseDto> createLote(@Valid @RequestBody LoteRequestDto loteRequestDto) {
        LoteResponseDto loteResponseDto = loteService.criarLote(loteRequestDto);
        return new ResponseEntity<>(loteResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Altera um lote por id", description = """
           # Alterar lote
           ---
           Esse endpoint altera os dados de um lote.
           ---
           Nota:
           - Id é obrigatório
           - Campos não passados não serão alterados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lote alterado com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoteResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Lote não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Campos de lote estão incorretos",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<LoteResponseDto> updateLote(@PathVariable Integer id,@Valid @RequestBody LoteRequestDto loteRequestDto) {
        LoteResponseDto loteResponseDto = loteService.updateLote(id, loteRequestDto);
        return new ResponseEntity<>(loteResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um lote por id", description = """
           # Deletar lote
           ---
           Esse endpoint deleta um lote.
           ---
           Nota:
           - Id é obrigatório
           - Uma vez deletado, não será possivel recuperar o lote.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lote deletado com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Lote não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<Void> deleteLote(@PathVariable Integer id) {
        loteService.excluirLote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Busca todos os lotes", description = """
           # Buscar lote
           ---
           Esse endpoint busca todos os lotes criados.
           ---
           Nota:
           - N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lotes encontrados com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoteResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Lotes não encontrado no banco de dados.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<LoteResponseDto>> getAllLotes() {
        List<LoteResponseDto> responseDtoList = loteService.listarLotes();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca lote por ID", description = """
           # Buscar Lote por id
           ---
           Esse endpoint busca um lote por id.
           ---
           Nota:
           - Id obrigatorio
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lote encontrado com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoteResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Lote não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<LoteResponseDto> getLoteById(@PathVariable Integer id) {
        LoteResponseDto loteResponseDto = loteService.obterLotePorId(id);
        return new ResponseEntity<>(loteResponseDto, HttpStatus.OK);
    }

    @GetMapping("/lotes-vencimento")
    @Operation(summary = "Busca 5 lotes proximos do vencimento", description = """
           # Buscar Lotes proximos do vencimento
           ---
           Esse endpoint busca os lotes mais próximos do vencimento.
           ---
           Nota:
           - N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lote encontrado com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoteResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Lote não encontrado.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<LotesProximoDoVencimentoResponse>> getLotesVencimento() {
        List<LotesProximoDoVencimentoResponse> response = loteService.getLotesVencimento();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/casa")
    @Operation(summary = "Busca todos os lotes da casa", description = """
           # Buscar lote
           ---
           Esse endpoint busca todos os lotes criados.
           ---
           Nota:
           - N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lotes encontrados com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoteResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Lotes não encontrado no banco de dados.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<LoteResponseDto>> getAllLotesCasa() {
        List<LoteResponseDto> responseDtoList = loteService.listarLoteCasa();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/estudio")
    @Operation(summary = "Busca todos os lotes do estudio", description = """
           # Buscar lote
           ---
           Esse endpoint busca todos os lotes criados.
           ---
           Nota:
           - N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lotes encontrados com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoteResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Lotes não encontrado no banco de dados.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<LoteResponseDto>> getAllLotesEstudio() {
        List<LoteResponseDto> responseDtoList = loteService.listarLoteEstudio();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }
}




