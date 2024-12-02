package com.velas.vivene.inventory.manager.controller;

import java.util.List;

import com.velas.vivene.inventory.manager.dto.pedido.PedidoCalendarioResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.velas.vivene.inventory.manager.dto.pedido.PedidoRequestDto;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoResponseDto;
import com.velas.vivene.inventory.manager.dto.quantidadevendasseismeses.QuantidadeVendasSeisMesesResponse;
import com.velas.vivene.inventory.manager.dto.top5pedidos.TopCincoPedidosResponse;
import com.velas.vivene.inventory.manager.service.PedidoService;

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
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedido Controller", description = "APIs para gerenciamento de pedidos")
@CrossOrigin(origins = "http://44.192.41.78:3000")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Cria um novo pedido", description = """
           # Criar pedido
           ---
           Esse endpoint cria um novo pedido
           ---
           Nota:
           Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos de pedido estão incorretos.",
                    content = @Content(mediaType = "application/json", schema = @Schema()))})
    public ResponseEntity<PedidoResponseDto> criarPedido(@Valid @RequestBody PedidoRequestDto pedidoRequest) {
        PedidoResponseDto novoPedido = pedidoService.criarPedido(pedidoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um pedido existente", description = """
           # Atualizar pedido
           ---
           Esse endpoint atualiza um pedido existente
           ---
           Nota:
           Todos os atributos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Campos de pedido estão incorretos.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "O id informado não existe.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})

    }
    )
    public ResponseEntity<PedidoResponseDto> updatePedido(@PathVariable Integer id, @Valid @RequestBody PedidoRequestDto pedidoRequestDTO) {
        PedidoResponseDto responseDTO = pedidoService.updatePedido(id, pedidoRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um pedido existente", description = """
           # Deletar pedido
           ---
           Esse endpoint deleta um pedido existente
           ---
           Nota:
           O pedido deve existir no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<Void> deletePedido(@PathVariable Integer id) {
        pedidoService.deletePedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Busca todos os pedidos", description = """
           # Busca todos os pedidos
           ---
           Esse endpoint busca todos os pedidos existentes
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos buscados com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhum pedido encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<PedidoResponseDto>> getAllPedidos() {
        List<PedidoResponseDto> responseDTOList = pedidoService.getAllPedidos();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/datas")
    @Operation(summary = "Busca todos os pedidos", description = """
           # Busca todos os pedidos
           ---
           Esse endpoint busca todos os pedidos existentes
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos buscados com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhum pedido encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<LocalDate>> getAllDates() {
        List<LocalDate> responseDTOList = pedidoService.getAllDates();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/datas/hoje")
    @Operation(summary = "Busca todos os pedidos", description = """
           # Busca todos os pedidos
           ---
           Esse endpoint busca todos os pedidos existentes
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos buscados com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhum pedido encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<LocalDate>> getAllDatesNow() {
        List<LocalDate> responseDTOList = pedidoService.getAllDatesFiltro();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/calendario")
    @Operation(summary = "Busca todos os pedidos detalhe calendario", description = """
           # Busca todos os pedidos
           ---
           Esse endpoint busca todos os pedidos existentes
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos buscados com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhum pedido encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<PedidoCalendarioResponseDto>> getAllPedidosCalendario() {
        List<PedidoCalendarioResponseDto> responseDTOList = pedidoService.getAllPedidosCalendario();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/filtro/data/now")
    @Operation(summary = "Busca todos os pedidos com filtro", description = """
           # Busca todos os pedidos com filtro
           ---
           Esse endpoint busca todos os pedidos existentes com filtro
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos buscados com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhum pedido encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<PedidoResponseDto>> getAllPedidosFiltroNow(@RequestParam(required = false) String nomeVela, @RequestParam(required = false) LocalDate data, @RequestParam(required = false) String nomeCliente) {
        List<PedidoResponseDto> responseDTOList = pedidoService.getAllPedidosFiltroNow(data, nomeCliente, nomeVela);
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/filtro")
    @Operation(summary = "Busca todos os pedidos com filtro", description = """
           # Busca todos os pedidos com filtro
           ---
           Esse endpoint busca todos os pedidos existentes com filtro
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos buscados com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhum pedido encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<LocalDate>> getAllPedidosFiltro(@RequestParam(required = false) String nomeVela, @RequestParam(required = false) LocalDate data, @RequestParam(required = false) String nomeCliente) {
        List<LocalDate> responseDTOList = pedidoService.getAllPedidosFiltro(data, nomeCliente, nomeVela);
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um pedido pelo ID", description = """
           # Busca pedido pelo id
           ---
           Esse endpoint busca um pedido existente pelo id
           ---
           Nota:
           O id é obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<PedidoResponseDto> getPedidoById(@PathVariable Integer id) {
        PedidoResponseDto responseDTO = pedidoService.getPedidoById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/quantidade-vendas")
    @Operation(summary = "Busca a quantidade de vendas por mês em um período de seis meses", description = """
           # Busca quantidade de vendas
           ---
           Esse endpoint busca a quantidade de vendas por mês em um período de seis meses
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade de vendas encontrada com sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = QuantidadeVendasSeisMesesResponse.class))}),
            @ApiResponse(responseCode = "204", description = "Nenhuma venda encontrada.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})})
    public ResponseEntity<List<QuantidadeVendasSeisMesesResponse>> getQuantidadeVendasSeisMeses() {
        List<QuantidadeVendasSeisMesesResponse> responses = pedidoService.getQuantidadeVendasSeisMeses();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/top-cinco-pedidos")
    @Operation(summary= "Busca os cinco pedidos mais próximos da data de entrega", description= """
                    # Busca top cinco pedidos mais próximo do vencimento
                    ---
                    Esse endpoint busca os top cinco pedidos mais próximo do vencimento, data atual mais próxima
                    """)
        @ApiResponses(value={
                @ApiResponse(responseCode="200", description="Top cinco pedidos encontrado com sucesso.",
                content= {@Content(mediaType= "application/json", schema=@Schema(implementation=TopCincoPedidosResponse.class))}),
                @ApiResponse(responseCode="204", description="Nenhum pedido encontrado.", 
                content={ @Content(mediaType="application/json", schema= @Schema())})})
        public ResponseEntity<List<TopCincoPedidosResponse>> getTopCincoPedidos() {
                return new ResponseEntity<>(pedidoService.getTopCincoPedidos(), HttpStatus.OK);
        }
        
}