package com.velas.vivene.inventory.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velas.vivene.inventory.manager.controller.LoteController;
import com.velas.vivene.inventory.manager.dto.lote.LoteRequestDto;
import com.velas.vivene.inventory.manager.dto.lote.LoteResponseDto;
import com.velas.vivene.inventory.manager.dto.lotesproximodovencimento.LotesProximoDoVencimentoResponse;
import com.velas.vivene.inventory.manager.service.LoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(LoteController.class)
public class LoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoteService loteService;

    @Autowired
    private ObjectMapper objectMapper;

    private LoteRequestDto loteRequestDto;
    private LoteResponseDto loteResponseDto;
    private List<LoteResponseDto> loteResponseDtoList;
    private List<LotesProximoDoVencimentoResponse> lotesProximoDoVencimentoResponseList;

    @BeforeEach
    void setUp() {
        loteRequestDto = new LoteRequestDto();
        loteRequestDto.setFkVela(1);
        loteRequestDto.setQuantidade(100);
        loteRequestDto.setDataFabricacao(LocalDate.parse("2024-01-01"));
        loteRequestDto.setDataValidade(LocalDate.parse("2024-12-31"));
        loteRequestDto.setLocalizacao(1);

        loteResponseDto = new LoteResponseDto();
        loteResponseDto.setId(1);
        loteResponseDto.setFkVela(1);
        loteResponseDto.setQuantidade(100);
        loteResponseDto.setDataFabricacao(LocalDate.parse("2024-01-01"));
        loteResponseDto.setDataValidade(LocalDate.parse("2024-12-31"));
        loteResponseDto.setLocalizacao(1);

        loteResponseDtoList = List.of(loteResponseDto);

        lotesProximoDoVencimentoResponseList = List.of(
                new LotesProximoDoVencimentoResponse()
        );
    }

    @Test
    @DisplayName("Teste para criar um novo lote")
    void testCreateLote() throws Exception {
        when(loteService.criarLote(any(LoteRequestDto.class))).thenReturn(loteResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/lotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loteRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fkVela").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantidade").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataFabricacao").value("2024-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataValidade").value("2024-12-31"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.localizacao").value(1))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para atualizar um lote existente")
    void testUpdateLote() throws Exception {
        when(loteService.updateLote(anyInt(), any(LoteRequestDto.class))).thenReturn(loteResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/lotes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loteRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fkVela").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantidade").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataFabricacao").value("2024-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataValidade").value("2024-12-31"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.localizacao").value(1))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para deletar um lote existente")
    void testDeleteLote() throws Exception {
        doNothing().when(loteService).excluirLote(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/lotes/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para buscar todos os lotes")
    void testGetAllLotes() throws Exception {
        when(loteService.listarLotes()).thenReturn(loteResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/lotes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fkVela").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantidade").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataFabricacao").value("2024-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataValidade").value("2024-12-31"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].localizacao").value(1))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para buscar um lote por ID")
    void testGetLoteById() throws Exception {
        when(loteService.obterLotePorId(anyInt())).thenReturn(loteResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/lotes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fkVela").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantidade").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataFabricacao").value("2024-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataValidade").value("2024-12-31"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.localizacao").value(1))
                .andDo(print());
    }
}
