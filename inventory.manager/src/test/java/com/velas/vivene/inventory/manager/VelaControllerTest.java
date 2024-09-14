package com.velas.vivene.inventory.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velas.vivene.inventory.manager.controller.VelaController;
import com.velas.vivene.inventory.manager.dto.vela.VelaRequestDto;
import com.velas.vivene.inventory.manager.dto.vela.VelaResponseDto;
import com.velas.vivene.inventory.manager.entity.Vela;
import com.velas.vivene.inventory.manager.service.VelaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(VelaController.class)
public class VelaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VelaService velaService;

    @Autowired
    private ObjectMapper objectMapper;

    private VelaRequestDto velaRequestDto;
    private VelaResponseDto velaResponseDto;

    @BeforeEach
    public void setUp() {
        velaRequestDto = new VelaRequestDto();
        velaRequestDto.setNome("Vela Azul");
        velaRequestDto.setTamanho("Médio");
        velaRequestDto.setDescricao("Descrição da vela");
        velaRequestDto.setPreco(25.0);

        velaResponseDto = new VelaResponseDto();
        velaResponseDto.setId(1);
        velaResponseDto.setNome("Vela Azul");
        velaResponseDto.setTamanho("Médio");
        velaResponseDto.setDescricao("Descrição da vela");
        velaResponseDto.setPreco(25.0);
    }

    @Test
    public void createVela() throws Exception {
        when(velaService.createVela(any(VelaRequestDto.class))).thenReturn(velaResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/velas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(velaRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(velaResponseDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(velaResponseDto.getNome()));
    }

    @Test
    public void updateVela() throws Exception {
        when(velaService.updateVela(anyInt(), any(VelaRequestDto.class))).thenReturn(velaResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/velas/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(velaRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(velaResponseDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(velaResponseDto.getNome()));
    }

    @Test
    public void deleteVela() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/velas/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void getAllVelas() throws Exception {
        when(velaService.getAllVelas()).thenReturn(Collections.singletonList(velaResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/velas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(velaResponseDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value(velaResponseDto.getNome()));
    }

    @Test
    public void getVelaById() throws Exception {
        when(velaService.getVelaById(anyInt())).thenReturn(velaResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/velas/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(velaResponseDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(velaResponseDto.getNome()));
    }
}
