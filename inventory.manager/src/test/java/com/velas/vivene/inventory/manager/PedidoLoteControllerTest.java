package com.velas.vivene.inventory.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velas.vivene.inventory.manager.controller.PedidoLoteController;
import com.velas.vivene.inventory.manager.dto.pedidolote.PedidoLoteRequestDto;
import com.velas.vivene.inventory.manager.dto.pedidolote.PedidoLoteResponseDto;
import com.velas.vivene.inventory.manager.service.PedidoLoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoLoteController.class)
class PedidoLoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoLoteService pedidoLoteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Cria um novo pedido de lote com sucesso")
    void createPedidoLote() throws Exception {
        PedidoLoteRequestDto pedidoLoteRequestDto = new PedidoLoteRequestDto();
        pedidoLoteRequestDto.setLoteId(1);
        pedidoLoteRequestDto.setPedidoId(2);
        pedidoLoteRequestDto.setQuantidade(100);

        PedidoLoteResponseDto pedidoLoteResponseDto = new PedidoLoteResponseDto();
        pedidoLoteResponseDto.setId(1);
        pedidoLoteResponseDto.setFkLote(1);
        pedidoLoteResponseDto.setFkPedido(2);

        when(pedidoLoteService.createPedidoLote(any(PedidoLoteRequestDto.class))).thenReturn(pedidoLoteResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/pedidos-lotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoLoteRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(pedidoLoteResponseDto)));
    }

    @Test
    @DisplayName("Atualiza um pedido de lote existente com sucesso")
    void updatePedidoLote() throws Exception {
        PedidoLoteRequestDto pedidoLoteRequestDto = new PedidoLoteRequestDto();
        pedidoLoteRequestDto.setLoteId(1);
        pedidoLoteRequestDto.setPedidoId(3);
        pedidoLoteRequestDto.setQuantidade(200);

        PedidoLoteResponseDto pedidoLoteResponseDto = new PedidoLoteResponseDto();
        pedidoLoteResponseDto.setId(1);
        pedidoLoteResponseDto.setFkLote(1);
        pedidoLoteResponseDto.setFkPedido(3);

        when(pedidoLoteService.updatePedidoLote(anyInt(), any(PedidoLoteRequestDto.class)))
                .thenReturn(pedidoLoteResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/pedidos-lotes/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoLoteRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pedidoLoteResponseDto)));
    }

    @Test
    @DisplayName("Deleta um pedido de lote existente com sucesso")
    void deletePedidoLote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/pedidos-lotes/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Busca todos os pedidos de lote com sucesso")
    void getAllPedidoLotes() throws Exception {
        PedidoLoteResponseDto pedidoLoteResponseDto = new PedidoLoteResponseDto();
        pedidoLoteResponseDto.setId(1);
        pedidoLoteResponseDto.setFkLote(1);
        pedidoLoteResponseDto.setFkPedido(2);

        List<PedidoLoteResponseDto> pedidoLoteResponseDtoList = Collections.singletonList(pedidoLoteResponseDto);

        when(pedidoLoteService.getAllPedidoLote()).thenReturn(pedidoLoteResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/pedidos-lotes"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pedidoLoteResponseDtoList)));
    }

    @Test
    @DisplayName("Busca um pedido de lote pelo ID com sucesso")
    void getPedidoLoteById() throws Exception {
        PedidoLoteResponseDto pedidoLoteResponseDto = new PedidoLoteResponseDto();
        pedidoLoteResponseDto.setId(1);
        pedidoLoteResponseDto.setFkLote(1);
        pedidoLoteResponseDto.setFkPedido(2);

        when(pedidoLoteService.getPedidoLoteById(anyInt())).thenReturn(pedidoLoteResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/pedidos-lotes/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pedidoLoteResponseDto)));
    }
}
