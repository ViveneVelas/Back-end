package com.velas.vivene.inventory.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velas.vivene.inventory.manager.controller.PedidoController;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoRequestDto;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoResponseDto;
import com.velas.vivene.inventory.manager.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private PedidoRequestDto pedidoRequestDto;
    private PedidoResponseDto pedidoResponseDto;
    private List<PedidoResponseDto> pedidoResponseDtoList;

    @BeforeEach
    void setUp() {
        pedidoRequestDto = new PedidoRequestDto();
        pedidoRequestDto.setDtPedido(LocalDate.parse("2024-01-01"));
        pedidoRequestDto.setPreco(100.0);
        pedidoRequestDto.setDescricao("Descrição do pedido");
        pedidoRequestDto.setTipoEntrega("Entrega rápida");
        pedidoRequestDto.setStatus("Pendente");
        pedidoRequestDto.setClienteId(1);  // Definindo o cliente

        pedidoResponseDto = new PedidoResponseDto();
        pedidoResponseDto.setId(1);
        pedidoResponseDto.setDtPedido(LocalDate.parse("2024-01-01"));
        pedidoResponseDto.setPreco(100.0);
        pedidoResponseDto.setDescricao("Descrição do pedido");
        pedidoResponseDto.setTipoEntrega("Entrega rápida");
        pedidoResponseDto.setStatus("Pendente");

        pedidoResponseDtoList = List.of(pedidoResponseDto);
    }

    @Test
    @DisplayName("Teste para criar um novo pedido")
    void testCreatePedido() throws Exception {
        // Configura o comportamento esperado do serviço
        when(pedidoService.criarPedido(any(PedidoRequestDto.class))).thenReturn(pedidoResponseDto);

        // Realiza a requisição POST e verifica a resposta
        mockMvc.perform(MockMvcRequestBuilders.post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())  // Espera-se o status 201
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dtPedido").value("2024-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preco").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Descrição do pedido"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoEntrega").value("Entrega rápida"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Pendente"))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para atualizar um pedido existente")
    void testUpdatePedido() throws Exception {
        // Configura o comportamento esperado do serviço
        when(pedidoService.updatePedido(anyInt(), any(PedidoRequestDto.class))).thenReturn(pedidoResponseDto);

        // Realiza a requisição PUT e verifica a resposta
        mockMvc.perform(MockMvcRequestBuilders.put("/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())  // Espera-se o status 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dtPedido").value("2024-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preco").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Descrição do pedido"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoEntrega").value("Entrega rápida"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Pendente"))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para deletar um pedido existente")
    void testDeletePedido() throws Exception {
        doNothing().when(pedidoService).deletePedido(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/pedidos/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para buscar todos os pedidos")
    void testGetAllPedidos() throws Exception {
        when(pedidoService.getAllPedidos()).thenReturn(pedidoResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/pedidos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dtPedido").value("2024-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].preco").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value("Descrição do pedido"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tipoEntrega").value("Entrega rápida"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("Pendente"))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para buscar um pedido por ID")
    void testGetPedidoById() throws Exception {
        when(pedidoService.getPedidoById(anyInt())).thenReturn(pedidoResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dtPedido").value("2024-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preco").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Descrição do pedido"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoEntrega").value("Entrega rápida"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Pendente"))
                .andDo(print());
    }
}
