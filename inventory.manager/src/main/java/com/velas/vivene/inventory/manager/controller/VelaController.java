package com.velas.vivene.inventory.manager.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.velas.vivene.inventory.manager.dto.clientesmaiscompras.ClienteMaisComprasResponse;
import com.velas.vivene.inventory.manager.dto.top5velasmaisvendidas.Top5VelasMaisVendidasResponse;
import com.velas.vivene.inventory.manager.dto.vela.VelaRequestDto;
import com.velas.vivene.inventory.manager.dto.vela.VelaResponseDto;
import com.velas.vivene.inventory.manager.dto.velamaisvendida.VelaMaisVendidaResponse;
import com.velas.vivene.inventory.manager.service.VelaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/velas")
@RequiredArgsConstructor
@Tag(name = "Vela Controller", description = "APIs para gerenciamento de velas")
@CrossOrigin(origins = "http://3.87.199.254:3000")
public class VelaController {

    private final VelaService velaService;

    @PostMapping
    @Operation(summary = "Cria uma nova vela", description = """
           # Criar vela
           ---
           Esse endpoint cria uma nova vela
           ---
           Nota:
           Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vela criada com sucesso (OK).",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = VelaResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos de vela estão incorretos",
            content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<VelaResponseDto> createVela(
        @RequestParam("nome") String nome,
        @RequestParam("tamanho") String tamanho,
        @RequestParam("preco") Double preco,
        @RequestParam("descricao") String descricao,
        @RequestParam("imagem") MultipartFile imagem)
    {
        VelaResponseDto responseDTO = velaService.createVela(nome, tamanho, preco, descricao, imagem);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma vela existente", description = """
           # Atualizar vela
           ---
           Esse endpoint atualiza uma vela existente
           ---
           Nota:
           Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vela atualizada com sucesso (OK).",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = VelaResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos de vela estão incorretos.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "ID da vela não existe",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<VelaResponseDto> updateVela(@PathVariable Integer id, @RequestParam("nome") String nome,
    @RequestParam("tamanho") String tamanho,
    @RequestParam("preco") Double preco,
    @RequestParam("descricao") String descricao,
    @RequestParam("imagem") MultipartFile imagem) {
        VelaResponseDto responseDTO = velaService.updateVela(id, nome, tamanho, preco, descricao, imagem);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma vela existente", description = """
           # Deletar vela
           ---
           Esse endpoint deleta uma vela existente
           ---
           Nota:
           A vela deve existir no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vela deletada com sucesso (OK).",
                    content = { @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Buffet não existe no banco de dados.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<Void> deleteVela(@PathVariable Integer id) {
        velaService.deleteVela(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Busca todas as velas", description = """
           # Busca todas as velas
           ---
           Esse endpoint busca todas as velas existentes
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Velas buscadas com sucesso (OK).",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = VelaResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Não há velas no banco de dados.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<List<VelaResponseDto>> getAllVelas() {
        List<VelaResponseDto> responseDTOList = velaService.getAllVelas();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma vela pelo ID", description = """
           # Busca uma vela existente pelo id
           ---
           Esse endpoint busca uma vela existente pelo id
           ---
           Nota:
           O id é obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vela encontrada com sucesso (OK).",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VelaResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Vela não encontrada.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<VelaResponseDto> getVelaById(@PathVariable Integer id) throws IOException {
        VelaResponseDto responseDTO = velaService.getVelaById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/maisvendida")
    @Operation(summary = "Buscar vela mais vendida", description = """
           # Busca a vela mais vendida
           ---
           Esse endpoint busca a vela mais vendida
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vela mais vendida encontrada com sucesso (OK).",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VelaMaisVendidaResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Vela não encontrada.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<List<VelaMaisVendidaResponse>> getVelaVendida() {
        List<VelaMaisVendidaResponse> responseDTO = velaService.getVelaVendida();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/top-mais-vendidas")
    @Operation(summary = "Buscar as 5 velas mais vendidas", description = """
           # Busca as top cinco velas mais vendida
           ---
           Esse endpoint busca as top cinco velas mais vendida
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Top cinco velas mais vendidas encontrada com sucesso (OK).",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Top5VelasMaisVendidasResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Vela não encontrada.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<List<Top5VelasMaisVendidasResponse>> getTop5VelasVendidas() {
        List<Top5VelasMaisVendidasResponse> top5VelasMaisVendidasResponses = velaService.getTop5VelasVendidas();
        return new ResponseEntity<>(top5VelasMaisVendidasResponses, HttpStatus.OK);
    }

    @GetMapping("/buscar-vela-nome/{nome}")
    @Operation(summary = "Buscar velas pelo nome", description = """
           # Busca as top cinco velas mais vendida
           ---
           Esse endpoint busca as top cinco velas mais vendida
           ---
           Nota:
           O nome da vela é obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vela encontrada com sucesso (OK).",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Top5VelasMaisVendidasResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Vela não encontrada.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<List<VelaResponseDto>> getVelasByName(@PathVariable String nome) {
        List<VelaResponseDto> response = velaService.getVelasByName(nome);
        if (response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/arq-criar/top-cinco-velas")
    @Operation(summary = "Cria um arquivo csv Top 5 velas", description = """
           # Cria um arquivo de 5 velas mais vendidas
           ---
           Esse endpoint cria um arquivo de csv das 5 velas mais vendidas
           ---
           Nota:
           - Nome do arquivo é obrigatorio
           - Não informar tipo no final (.txt ou .csv ou derivados)
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteMaisComprasResponse.class))})})
    public ResponseEntity<byte[]> criarTxtClientes(@RequestParam String nomeArq) throws IOException {
        byte[] arquivo = velaService.criarArqCsv();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", nomeArq + ".csv");

        return new ResponseEntity<>(arquivo, headers, HttpStatus.OK);
    }

    @GetMapping("/buscar-nomes-velas")
    @Operation(summary = "Busca todos os nomes das velas", description = """
           # Busca todos os nomes das velas
           ---
           Esse endpoint busca todas as velas existentes
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Velas buscadas com sucesso (OK).",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = VelaResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Não há velas no banco de dados.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<List<VelaResponseDto>> getAllNamesVelas() {
        List<VelaResponseDto> responseDTOList = velaService.getAllNamesVelas();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/filtro/{filtro}")
    @Operation(summary = "Busca todos os nomes das velas", description = """
           # Busca todos os nomes das velas
           ---
           Esse endpoint busca todas as velas existentes
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Velas buscadas com sucesso (OK).",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = VelaResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Não há velas no banco de dados.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<List<VelaResponseDto>> filtroVelas(@PathVariable String filtro) {
        List<VelaResponseDto> responseDTOList = velaService.filtroVelas(filtro);
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/filtro-nome/{nome}")
    @Operation(summary = "Busca uma vela pelo Nome", description = """
           # Busca uma vela existente pelo Nome
           ---
           Esse endpoint busca uma vela existente pelo Nome
           ---
           Nota:
           O Nome é obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vela encontrada com sucesso (OK).",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VelaResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Vela não encontrada.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<List<VelaResponseDto>> getNomeBusca(@PathVariable String nome) throws IOException {
        List<VelaResponseDto> responseDTO = velaService.getNomeBusca(nome);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}