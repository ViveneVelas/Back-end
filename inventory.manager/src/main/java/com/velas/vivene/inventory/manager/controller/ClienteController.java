package com.velas.vivene.inventory.manager.controller;

import com.velas.vivene.inventory.manager.dto.cliente.ClienteRequestDto;
import com.velas.vivene.inventory.manager.dto.cliente.ClienteResponseDto;
import com.velas.vivene.inventory.manager.dto.clientesmaiscompras.ClienteMaisComprasResponse;
import com.velas.vivene.inventory.manager.dto.lote.LoteResponseDto;
import com.velas.vivene.inventory.manager.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Cliente Controller", description = "APIs para gerenciamento de clientes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Criar cliente", description = """
           # Criar cliente
           ---
           Esse endpoint busca clientes por id
           ---
           Nota:
           - Todos os campos são obrigatórios
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clientes criado com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Dados do cliente incorretos.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    @Operation(summary = "Cria um novo Cliente", description = """
          # Cria Cliente
          ---
          Esse endpoint é responsavel por criar novos clientes que são associados a pedidos.
          
          notes:
          - Todos os campos são requiridos;
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente Criado com Sucesso.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Algum campo está incorreto.",
                    content = { @Content(mediaType = "application/json", schema = @Schema())})}
    )
    public ResponseEntity<ClienteResponseDto> createCliente(@Valid @RequestBody ClienteRequestDto clienteRequestDTO) {
        ClienteResponseDto responseDTO = clienteService.createCliente(clienteRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza cliente por id", description = """
           # Atualizar cliente por id
           ---
           Esse endpoint atualiza clientes por id
           ---
           Nota:
           - Id Obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente Alterado com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    @Operation(summary = "Atualiza um Cliente", description = """
          # Atualiza Cliente
          ---
          Esse endpoint é responsavel por atualizar os dados de um cliente.
          
          notes:
          - O id do cliente é obrigatório;
          - O cliente precisa existir no banco de dados;
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
    public ResponseEntity<ClienteResponseDto> updateCliente(@PathVariable Integer id, @Valid @RequestBody ClienteRequestDto clienteRequestDTO) {
        ClienteResponseDto responseDTO = clienteService.updateCliente(id, clienteRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente por id", description = """
           # Deletar cliente por id
           ---
           Esse endpoint deleta um clientes por id
           ---
           Nota:
           - Id Obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    @Operation(summary = "Deleta um Cliente", description = """
          # Deleta Cliente
          ---
          Esse endpoint é responsavel por deletar um cliente.
          
          notes:
          - O id do cliente é obrigatório;
          - O cliente precisa existir no banco de dados;
          - Uma vez deletado não é possivel buscar de volta.
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Cliente deletado com sucesso.",
                    content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.",
                    content = { @Content(schema = @Schema())})
    }
    )
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Buscar todos os cliente", description = """
           # Buscar todos os clientes
           ---
           Esse endpoint busca clientes
           ---
           Nota:
           - N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Clientes não encontrado (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})
    @Operation(summary = "Busca Todos os Cliente", description = """
          # Busca Clientes
          ---
          Esse endpoint é responsavel por buscar todos os clientes.
          
          notes:
          - N/A
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com Sucesso.",
                    content = { @Content(schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Não existe cliente no banco de dados.",
                    content = { @Content(schema = @Schema())})
    }
    )
    public ResponseEntity<List<ClienteResponseDto>> getAllClientes() {
        List<ClienteResponseDto> responseDTOList = clienteService.getAllClientes();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por id", description = """
           # Buscar cliente por id
           ---
           Esse endpoint busca clientes por id
           ---
           Nota:
           - Id Obrigatório
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Clientes não encontrado (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema())})})

    @Operation(summary = "Busca um cliente por ID", description = """
          # Busca um Cliente
          ---
          Esse endpoint é responsavel por buscar um cliente.
          
          notes:
          - O id do cliente é obrigatório;
          - O cliente precisa existir no banco de dados.
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente Encontrado com sucesso.",
                    content = { @Content(schema = @Schema(implementation = ClienteResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.",
                    content = { @Content(schema = @Schema())})
        }
    )
    public ResponseEntity<ClienteResponseDto> getClienteById(@PathVariable Integer id) {
        ClienteResponseDto responseDTO = clienteService.getClienteById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/clientes-mais-compras")
    @Operation(summary = "Buscar clientes com mais compras", description = """
           # Busca clientes com mais compras
           ---
           Esse endpoint busca clientes com mais compras
           ---
           Nota:
           - N/A
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteMaisComprasResponse.class))})})

    @GetMapping("/clientes-mais-compras")
    @Operation(summary = "Busca os 5 clientes com mais compras", description = """
          # Busca um top de 5 Clientes que mais fizeram compras
          ---
          Esse endpoint é responsavel por buscar top 5 clientes.
          
          notes:
          - O cliente precisa existir no banco de dados.
          """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente Encontrado com sucesso.",
                    content = { @Content(schema = @Schema(implementation = ClienteMaisComprasResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Cliente não cadastrado.",
                    content = { @Content(schema = @Schema())})
    }
    )
    public ResponseEntity<List<ClienteMaisComprasResponse>> getClienteMaisCompras() {
        List<ClienteMaisComprasResponse> responseDTO = clienteService.getClienteMaisCompras();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/arq-criar/clientes-mais-compras")
    @Operation(summary = "Cria um arquivo txt clientes com mais compras", description = """
           # Cria um arquivo de clientes com mais compras
           ---
           Esse endpoint cria um arquivo de texto clientes com mais compras
           ---
           Nota:
           - Nome do arquivo é obrigatorio
           - Não informar tipo no final (.txt ou .csv ou derivados)
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteMaisComprasResponse.class))})})
    public ResponseEntity<byte[]> criarTxtClientes(@RequestParam String nomeArq) throws IOException {
        byte[] arquivo = clienteService.criarArqTxt();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", nomeArq + ".txt");

        return new ResponseEntity<>(arquivo, headers, HttpStatus.OK);
    }

    @PostMapping("/arq-ler/clientes-mais-compras")
    @Operation(summary = "Lê um arquivo txt de clientes com mais compras", description = """
           # Lê um arquivo de clientes com mais compras
           ---
           Esse endpoint lê um arquivo de texto clientes com mais compras enviado como `byte[]`
           ---
           Nota:
           - Enviar o arquivo em formato `.txt` no corpo da requisição como um `byte[]`.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo lido com sucesso (OK).",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClienteMaisComprasResponse.class))})})
    public void lerTxtClientes(@RequestBody byte[] fileContent) throws IOException {
        clienteService.lerArqTxt(fileContent);
    }

}