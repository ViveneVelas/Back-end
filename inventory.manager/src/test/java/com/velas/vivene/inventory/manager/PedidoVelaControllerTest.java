package com.velas.vivene.inventory.manager;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velas.vivene.inventory.manager.controller.PedidoVelaController;
import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaRequestDto;
import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaResponseDto;
import com.velas.vivene.inventory.manager.service.PedidoVelaService;

@WebMvcTest(PedidoVelaController.class)
class PedidoVelaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoVelaService pedidoVelaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Cria um novo pedido vela com sucesso")
    void createPedidoVela() throws Exception {
        PedidoVelaRequestDto pedidoVelaRequestDto = new PedidoVelaRequestDto();
        pedidoVelaRequestDto.setVelaId(1);
        pedidoVelaRequestDto.setPedidoId(2);
        pedidoVelaRequestDto.setQuantidade(100);

        PedidoVelaResponseDto pedidoVelaResponseDto = new PedidoVelaResponseDto();
        pedidoVelaResponseDto.setId(1);
        pedidoVelaResponseDto.setFkVela(1);
        pedidoVelaResponseDto.setFkPedido(2);

        when(pedidoVelaService.createPedidoVela(any(PedidoVelaRequestDto.class))).thenReturn(pedidoVelaResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/pedidos-velas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoVelaRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(pedidoVelaResponseDto)));
    }

    @Test
    @DisplayName("Atualiza um pedido vela existente com sucesso")
    void updatePedidoVela() throws Exception {
        PedidoVelaRequestDto pedidoVelaRequestDto = new PedidoVelaRequestDto();
        pedidoVelaRequestDto.setVelaId(1);
        pedidoVelaRequestDto.setPedidoId(3);
        pedidoVelaRequestDto.setQuantidade(200);

        PedidoVelaResponseDto pedidoVelaResponseDto = new PedidoVelaResponseDto();
        pedidoVelaResponseDto.setId(1);
        pedidoVelaResponseDto.setFkVela(1);
        pedidoVelaResponseDto.setFkPedido(3);

        when(pedidoVelaService.updatePedidoVela(anyInt(), any(PedidoVelaRequestDto.class)))
                .thenReturn(pedidoVelaResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/pedidos-velas/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoVelaRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pedidoVelaResponseDto)));
    }

    @Test
    @DisplayName("Deleta um pedido vela existente com sucesso")
    void deletePedidoVela() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/pedidos-velas/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Busca todos os pedidos vela com sucesso")
    void getAllPedidoVela() throws Exception {
        PedidoVelaResponseDto pedidoVelaResponseDto = new PedidoVelaResponseDto();
        pedidoVelaResponseDto.setId(1);
        pedidoVelaResponseDto.setFkVela(1);
        pedidoVelaResponseDto.setFkPedido(2);

        List<PedidoVelaResponseDto> pedidoVelaResponseDtoList = Collections.singletonList(pedidoVelaResponseDto);

        when(pedidoVelaService.getAllPedidoVela()).thenReturn(pedidoVelaResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/pedidos-velas"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pedidoVelaResponseDtoList)));
    }

    @Test
    @DisplayName("Busca um pedido vela pelo ID com sucesso")
    void getPedidoVelaById() throws Exception {
        PedidoVelaResponseDto pedidoVelaResponseDto = new PedidoVelaResponseDto();
        pedidoVelaResponseDto.setId(1);
        pedidoVelaResponseDto.setFkVela(1);
        pedidoVelaResponseDto.setFkPedido(2);

        when(pedidoVelaService.getPedidoVelaById(anyInt())).thenReturn(pedidoVelaResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/pedidos-velas/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pedidoVelaResponseDto)));
    }
}
