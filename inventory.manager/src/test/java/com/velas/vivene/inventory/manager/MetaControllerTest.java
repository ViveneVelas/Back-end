package com.velas.vivene.inventory.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velas.vivene.inventory.manager.controller.MetaController;
import com.velas.vivene.inventory.manager.dto.meta.MetaRequestDto;
import com.velas.vivene.inventory.manager.dto.meta.MetaResponseDto;
import com.velas.vivene.inventory.manager.service.MetaService;
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

@WebMvcTest(MetaController.class)
public class MetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetaService metaService;

    @Autowired
    private ObjectMapper objectMapper;

    private MetaRequestDto metaRequestDto;
    private MetaResponseDto metaResponseDto;
    private List<MetaResponseDto> metaResponseDtoList;

    @BeforeEach
    void setUp() {
        metaRequestDto = new MetaRequestDto();
        metaRequestDto.setDataInicio(LocalDate.parse("2024-01-01"));
        metaRequestDto.setDataFinal(LocalDate.parse("2024-06-30"));
        metaRequestDto.setQtdVendas(100);

        metaResponseDto = new MetaResponseDto();
        metaResponseDto.setId(1);
        metaResponseDto.setDataInicio(LocalDate.parse("2024-01-01"));
        metaResponseDto.setDataFinal(LocalDate.parse("2024-06-30"));
        metaResponseDto.setQtdVendas(100);

        metaResponseDtoList = List.of(metaResponseDto);
    }

    @Test
    @DisplayName("Teste para criar uma nova meta")
    void testCreateMeta() throws Exception {
        when(metaService.createMeta(any(MetaRequestDto.class))).thenReturn(metaResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/metas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metaRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataInicio").value("2024-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataFinal").value("2024-06-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.qtdVendas").value(100))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para atualizar uma meta existente")
    void testUpdateMeta() throws Exception {
        when(metaService.updateMeta(anyInt(), any(MetaRequestDto.class))).thenReturn(metaResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/metas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metaRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataInicio").value("2024-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataFinal").value("2024-06-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.qtdVendas").value(100))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para deletar uma meta existente")
    void testDeleteMeta() throws Exception {
        doNothing().when(metaService).deletarMeta(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/metas/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para buscar todas as metas")
    void testGetAllMetas() throws Exception {
        when(metaService.getAllMetas()).thenReturn(metaResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/metas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataInicio").value("2024-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataFinal").value("2024-06-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].qtdVendas").value(100))
                .andDo(print());
    }

    @Test
    @DisplayName("Teste para buscar uma meta por ID")
    void testGetMetaById() throws Exception {
        when(metaService.getMetaById(anyInt())).thenReturn(metaResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/metas/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataInicio").value("2024-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataFinal").value("2024-06-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.qtdVendas").value(100))
                .andDo(print());
    }
}
