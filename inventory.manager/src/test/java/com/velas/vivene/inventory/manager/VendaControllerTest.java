package com.velas.vivene.inventory.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velas.vivene.inventory.manager.controller.VendaController;
import com.velas.vivene.inventory.manager.dto.venda.VendaRequestDto;
import com.velas.vivene.inventory.manager.dto.venda.VendaResponseDto;
import com.velas.vivene.inventory.manager.service.VendaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VendaController.class)
public class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc; // Injeção automática de MockMvc

    @MockBean
    private VendaService vendaService; // Mockando o serviço corretamente

    @Test
    @DisplayName("Deve criar uma nova venda")
    public void createVenda() throws Exception {
        VendaRequestDto requestDto = new VendaRequestDto();
        requestDto.setPedidoId(1); // Adicione este campo se for necessário
        requestDto.setMetodoPag("Cartão");

        VendaResponseDto responseDto = new VendaResponseDto();
        responseDto.setId(1);
        responseDto.setPedidoId(1); // Adicione este campo se for necessário
        responseDto.setMetodoPag("Cartão");

        when(vendaService.createVenda(any(VendaRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/vendas")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.metodoPag").value("Cartão"));
    }

    @Test
    @DisplayName("Deve atualizar uma venda existente")
    public void updateVenda() throws Exception {
        VendaRequestDto requestDto = new VendaRequestDto();
        requestDto.setPedidoId(1); // Adicione este campo se for necessário
        requestDto.setMetodoPag("Dinheiro");

        VendaResponseDto responseDto = new VendaResponseDto();
        responseDto.setId(1);
        responseDto.setPedidoId(1); // Adicione este campo se for necessário
        responseDto.setMetodoPag("Dinheiro");

        when(vendaService.updateVenda(eq(1), any(VendaRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/vendas/1")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.metodoPag").value("Dinheiro"));
    }

    @Test
    @DisplayName("Deve deletar uma venda existente")
    public void deleteVenda() throws Exception {
        doNothing().when(vendaService).deleteVenda(1);

        mockMvc.perform(delete("/vendas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar todas as vendas")
    public void getAllVendas() throws Exception {
        VendaResponseDto responseDto = new VendaResponseDto();
        responseDto.setId(1);
        responseDto.setMetodoPag("Cartão");

        when(vendaService.getAllVendas()).thenReturn(Collections.singletonList(responseDto));

        mockMvc.perform(get("/vendas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].metodoPag").value("Cartão"));
    }

    @Test
    @DisplayName("Deve retornar uma venda pelo ID")
    public void getVendaById() throws Exception {
        VendaResponseDto responseDto = new VendaResponseDto();
        responseDto.setId(1);
        responseDto.setMetodoPag("Dinheiro");

        when(vendaService.getVendaById(1)).thenReturn(responseDto);

        mockMvc.perform(get("/vendas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.metodoPag").value("Dinheiro"));
    }
}
