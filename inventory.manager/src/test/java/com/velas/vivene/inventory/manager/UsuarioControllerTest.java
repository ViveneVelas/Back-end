package com.velas.vivene.inventory.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velas.vivene.inventory.manager.controller.UsuarioController;
import com.velas.vivene.inventory.manager.dto.usuario.UsuarioRequestDto;
import com.velas.vivene.inventory.manager.dto.usuario.UsuarioResponseDto;
import com.velas.vivene.inventory.manager.dto.login.LoginRequestDto;
import com.velas.vivene.inventory.manager.dto.login.LoginResponseDto;
import com.velas.vivene.inventory.manager.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UsuarioControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar um novo usuário")
    public void createUsuario() throws Exception {
        UsuarioRequestDto requestDto = new UsuarioRequestDto();
        requestDto.setNome("João");
        requestDto.setTelefone("123456789");
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("joao@teste.com");
        loginRequestDto.setSenha("senha123");
        requestDto.setLogin(loginRequestDto);

        UsuarioResponseDto responseDto = new UsuarioResponseDto();
        responseDto.setId(1);
        responseDto.setNome("João");
        responseDto.setTelefone("123456789");
        responseDto.setUltimoAcesso(LocalDate.now());
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setEmail("joao@teste.com");
        responseDto.setLogin(loginResponseDto);

        when(usuarioService.createUsuario(any(UsuarioRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.telefone").value("123456789"))
                .andExpect(jsonPath("$.ultimoAcesso").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.login.email").value("joao@teste.com"));
    }

    @Test
    @DisplayName("Deve atualizar um usuário existente")
    public void updateUsuario() throws Exception {
        UsuarioRequestDto requestDto = new UsuarioRequestDto();
        requestDto.setNome("João");
        requestDto.setTelefone("987654321");
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("joao@teste.com");
        loginRequestDto.setSenha("senha123");
        requestDto.setLogin(loginRequestDto);

        UsuarioResponseDto responseDto = new UsuarioResponseDto();
        responseDto.setId(1);
        responseDto.setNome("João");
        responseDto.setTelefone("987654321");
        responseDto.setUltimoAcesso(LocalDate.now());
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setEmail("joao@teste.com");
        responseDto.setLogin(loginResponseDto);

        when(usuarioService.updateUsuario(anyInt(), any(UsuarioRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.telefone").value("987654321"))
                .andExpect(jsonPath("$.ultimoAcesso").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.login.email").value("joao@teste.com"));
    }

    @Test
    @DisplayName("Deve deletar um usuário existente")
    public void deleteUsuario() throws Exception {
        doNothing().when(usuarioService).deleteUsuario(anyInt());

        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve buscar todos os usuários")
    public void getAllUsuarios() throws Exception {
        UsuarioResponseDto responseDto1 = new UsuarioResponseDto();
        responseDto1.setId(1);
        responseDto1.setNome("João");
        responseDto1.setTelefone("123456789");
        responseDto1.setUltimoAcesso(LocalDate.now());
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setEmail("joao@teste.com");
        responseDto1.setLogin(loginResponseDto);

        UsuarioResponseDto responseDto2 = new UsuarioResponseDto();
        responseDto2.setId(2);
        responseDto2.setNome("Maria");
        responseDto2.setTelefone("987654321");
        responseDto2.setUltimoAcesso(LocalDate.now());
        LoginResponseDto loginResponseDto1 = new LoginResponseDto();
        loginResponseDto1.setEmail("maria@teste.com");
        responseDto2.setLogin(loginResponseDto1);

        when(usuarioService.getAllUsuarios()).thenReturn(Arrays.asList(responseDto1, responseDto2));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[0].telefone").value("123456789"))
                .andExpect(jsonPath("$[0].ultimoAcesso").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].login.email").value("joao@teste.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Maria"))
                .andExpect(jsonPath("$[1].telefone").value("987654321"))
                .andExpect(jsonPath("$[1].ultimoAcesso").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[1].login.email").value("maria@teste.com"));
    }

    @Test
    @DisplayName("Deve buscar um usuário pelo ID")
    public void getUsuarioById() throws Exception {
        UsuarioResponseDto responseDto = new UsuarioResponseDto();
        responseDto.setId(1);
        responseDto.setNome("João");
        responseDto.setTelefone("123456789");
        responseDto.setUltimoAcesso(LocalDate.now());
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setEmail("joao@teste.com");
        responseDto.setLogin(loginResponseDto);

        when(usuarioService.getUsuarioById(anyInt())).thenReturn(responseDto);

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.telefone").value("123456789"))
                .andExpect(jsonPath("$.ultimoAcesso").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.login.email").value("joao@teste.com"));
    }
}
