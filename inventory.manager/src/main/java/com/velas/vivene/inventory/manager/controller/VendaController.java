package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.vela.VelaResponseDto;
import com.velas.vivene.inventory.manager.dto.cliente.ClienteResponseDto;
import com.velas.vivene.inventory.manager.dto.velamaisvendida.VelaMaisVendidaResponse;
import com.velas.vivene.inventory.manager.dto.venda.VendaRequestDto;
import com.velas.vivene.inventory.manager.dto.venda.VendaResponseDto;
import com.velas.vivene.inventory.manager.dto.vendasdasemana.VendasDaSemanaResponseDto;
import com.velas.vivene.inventory.manager.service.VendaService;
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
@RequestMapping("/vendas")
@RequiredArgsConstructor
@Tag(name = "Venda Controller", description = "APIs para gerenciamento de vendas")
@CrossOrigin(origins = "http://localhost:3000")
public class VendaController {

    private final VendaService vendaService;

    @PostMapping
    @Operation(summary = "Cria uma nova venda", description = """
           # Criar venda
           ---
           Esse endpoint transforma um pedido em uma venda final
           ---
           Nota:
           Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venda criada com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos de venda estão incorretos",
    @Operation(summary = "Cria uma nova Venda", description = """
          # Cria Venda
          ---
          Esse endpoint é responsavel por criar novas vendas.
          
          notes:
          - Precisa existir um pedido associado a venda;
          - Todos os campos são requiridos.
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente Criado com Sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Algum campo está incorreto.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<VendaResponseDto> createVenda(@Valid @RequestBody VendaRequestDto vendaRequestDTO) {
        VendaResponseDto responseDTO = vendaService.createVenda(vendaRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Altera uma venda por id", description = """
           # Alterar venda
           ---
           Esse endpoint altera os dados de uma venda.
           ---
           Nota:
           - Id é obrigatório
           - Campos não passados não serão alterados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda alterada com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Campos de venda estão incorretos",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    @Operation(summary = "Atualiza uma venda", description = """
          # Atualiza Venda
          ---
          Esse endpoint é responsavel por atualizar os dados de uma venda.
          
          notes:
          - O id da venda é obrigatório;
          - A venda precisa existir no banco de dados;
          - A venda precisa estar associada a um pedido;
          - Dados não passados, não serão alterados.
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente Alterado com Sucesso.",
                    content = { @Content(schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Algum campo está incorreto.",
                    content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.",
                    content = { @Content(schema = @Schema())})
    }
    )
    public ResponseEntity<VendaResponseDto> updateVenda(@PathVariable Integer id, @Valid @RequestBody VendaRequestDto vendaRequestDTO) {
        VendaResponseDto responseDTO = vendaService.updateVenda(id, vendaRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Busca vendas", description = """
           # Buscar todas as vendas
           ---
           Esse endpoint Busca todas as vendas cadastradas.
           ---
           Nota:
           - N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda  encontradas (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Não possui vendas no banco de dados",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma Venda", description = """
          # Deleta Venda
          ---
          Esse endpoint é responsavel por deletar uma venda.
          
          notes:
          - O id da venda é obrigatório;
          - A venda precisa existir no banco de dados;
          - Uma vez deletado não é possivel buscar de volta.
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Vedna deletado com sucesso.",
                    content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Venda não encontrado.",
                    content = { @Content(schema = @Schema())})
    }
    )
    public ResponseEntity<Void> deleteVenda(@PathVariable Integer id) {
        vendaService.deleteVenda(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Busca Todas as Vendas", description = """
          # Busca Vendas
          ---
          Esse endpoint é responsavel por buscar todas as vendas.
          
          notes:
          - N/A
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendas encontrados com Sucesso.",
                    content = { @Content(schema = @Schema(implementation = VendaResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Não existe venda no banco de dados.",
                    content = { @Content(schema = @Schema())})
    }
    )
    public ResponseEntity<List<VendaResponseDto>> getAllVendas() {
        List<VendaResponseDto> responseDTOList = vendaService.getAllVendas();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma venda por id", description = """
           # Buscar venda
           ---
           Esse endpoint busca uma venda a partir de um ID.
           ---
           Nota:
           - Id é obrigatório
           - Campos não passados não serão alterados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda encontrada com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    @Operation(summary = "Busca uma venda por ID", description = """
            # Busca uma Venda
            ---
            Esse endpoint é responsavel por buscar uma venda.
                      
            notes:
            - O id da venda é obrigatório;
            - A Venda precisa existir no banco de dados.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda Encontrada com sucesso.",
                    content = {@Content(schema = @Schema(implementation = VendaResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada.",
                    content = {@Content(schema = @Schema())})
    }
    )
               
    public ResponseEntity<VendaResponseDto> getVendaById(@PathVariable Integer id) {
        VendaResponseDto responseDTO = vendaService.getVendaById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma venda por id", description = """
           # Deletar venda
           ---
           Esse endpoint deleta os dados de uma venda.
           ---
           Nota:
           - Id é obrigatório
           - Quando deletada não será possivel recuperar
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venda deletada com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<Void> deleteVenda(@PathVariable Integer id) {
        vendaService.deleteVenda(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/semanal")
    @Operation(summary = "Busca vendas realizadas na semana", description = """
           # Buscar vendas semanais
           ---
           Esse endpoint busca as vendas semanais
           ---
           Nota:
           -N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendas encontradas com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Vendas não encontradas no banco de dados.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    @GetMapping("/semanal")
    @Operation(summary = "Busca vendas semanal", description = """
            # Busca Vendas semanais
            ---
            Esse endpoint é responsavel por buscar as informações de vendas realizadas na semana.
                      
            notes:
            - N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendas Encontrada com sucesso.",
                    content = {@Content(schema = @Schema(implementation = VendasDaSemanaResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Vendas não encontrada.",
                    content = {@Content(schema = @Schema())})
    }
    )
    public ResponseEntity<List<VendasDaSemanaResponseDto>> getVendaSemanal() {
        List<VendasDaSemanaResponseDto> responseDTO = vendaService.getVendaSemanal();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/semanal/quantidade")
    @Operation(summary = "Busca quantidade de vendas realizadas na semana", description = """
           # Buscar quantidade de vendas semanais
           ---
           Esse endpoint busca as quantidade de vendas semanais
           ---
           Nota:
           -N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade encontrada com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = VendaResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Vendas não encontradas no banco de dados.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    @Operation(summary = "Busca quantidade de vendas semanal", description = """
            # Busca Quantidade Vendas semanais
            ---
            Esse endpoint é responsavel por buscar a quantidade de vendas realizadas na semana.
                      
            notes:
            - N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade de Vendas Encontrada com sucesso.",
                    content = {@Content(schema = @Schema(implementation = Integer.class))}),
            @ApiResponse(responseCode = "404", description = "Vendas não encontrada.",
                    content = {@Content(schema = @Schema())})
    }
    )
    public ResponseEntity<Integer> getQtdVendaSemanal() {
        Integer qtd = vendaService.getQtdVendaSemanal();
        return new ResponseEntity<>(qtd, HttpStatus.OK);
    }
}
