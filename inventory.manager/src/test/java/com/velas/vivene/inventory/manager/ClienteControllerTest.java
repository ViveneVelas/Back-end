package com.velas.vivene.inventory.manager;

import com.velas.vivene.inventory.manager.controller.ClienteController;
import com.velas.vivene.inventory.manager.dto.cliente.ClienteRequestDto;
import com.velas.vivene.inventory.manager.dto.cliente.ClienteResponseDto;
import com.velas.vivene.inventory.manager.dto.clientesmaiscompras.ClienteMaisComprasResponse;
import com.velas.vivene.inventory.manager.service.ClienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    @DisplayName("Teste de criação de cliente")
    void createCliente() throws Exception {
        ClienteResponseDto mockResponse = new ClienteResponseDto();
        mockResponse.setId(1);
        mockResponse.setNome("João Silva");
        mockResponse.setTelefone("123456789");
        mockResponse.setQtdPedidos(10);
        Mockito.when(clienteService.createCliente(any(ClienteRequestDto.class)))
                .thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"João Silva\",\"telefone\":\"123456789\",\"qtdPedidos\":10}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"nome\":\"João Silva\",\"telefone\":\"123456789\",\"qtdPedidos\":10}"));
    }

    @Test
    @DisplayName("Testa a atualização de um cliente existente")
    void testUpdateCliente() throws Exception {
        ClienteRequestDto requestDto = new ClienteRequestDto();
        requestDto.setNome("Cliente Atualizado");
        requestDto.setTelefone("987654321");
        requestDto.setQtdPedidos(15);

        ClienteResponseDto responseDto = new ClienteResponseDto();
        responseDto.setId(1);
        responseDto.setNome("Cliente Atualizado");
        responseDto.setTelefone("987654321");
        responseDto.setQtdPedidos(15);

        requestDto.setNome("Cliente atualizado");
        requestDto.setTelefone("987654321");
        requestDto.setQtdPedidos(15);
        Mockito.when(clienteService.updateCliente(anyInt(), any(ClienteRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Cliente Atualizado\", \"telefone\": \"987654321\", \"qtdPedidos\": 15}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Cliente Atualizado"))
                .andExpect(jsonPath("$.telefone").value("987654321"))
                .andExpect(jsonPath("$.qtdPedidos").value(15));
    }

    @Test
    @DisplayName("Teste de deleção de cliente")
    void deleteCliente() throws Exception {
        Mockito.doNothing().when(clienteService).deleteCliente(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Testa a obtenção de todos os clientes")
    void testGetAllClientes() throws Exception {
        ClienteResponseDto cliente1 = new ClienteResponseDto();
        cliente1.setId(1);
        cliente1.setNome("Cliente 1");
        cliente1.setTelefone("123456789");
        cliente1.setQtdPedidos(10);

        ClienteResponseDto cliente2 = new ClienteResponseDto();
        cliente2.setId(2);
        cliente2.setNome("Cliente 2");
        cliente2.setTelefone("987654321");
        cliente2.setQtdPedidos(5);

        List<ClienteResponseDto> clientes = Arrays.asList(cliente1, cliente2);

        Mockito.when(clienteService.getAllClientes()).thenReturn(clientes);

        mockMvc.perform(get("/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Cliente 1"))
                .andExpect(jsonPath("$[0].telefone").value("123456789"))
                .andExpect(jsonPath("$[0].qtdPedidos").value(10))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Cliente 2"))
                .andExpect(jsonPath("$[1].telefone").value("987654321"))
                .andExpect(jsonPath("$[1].qtdPedidos").value(5));
    }


    @Test
    @DisplayName("Teste de obtenção de cliente por ID")
    void getClienteById() throws Exception {
        ClienteResponseDto mockResponse = new ClienteResponseDto();
        mockResponse.setId(1);
        mockResponse.setNome("João Silva");
        mockResponse.setTelefone("123456789");
        mockResponse.setQtdPedidos(10);
        Mockito.when(clienteService.getClienteById(anyInt())).thenReturn(mockResponse);

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"nome\":\"João Silva\",\"telefone\":\"123456789\",\"qtdPedidos\":10}"));
    }
}

