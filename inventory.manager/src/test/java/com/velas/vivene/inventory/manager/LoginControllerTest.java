package com.velas.vivene.inventory.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velas.vivene.inventory.manager.controller.LoginController;
import com.velas.vivene.inventory.manager.dto.login.LoginRequestDto;
import com.velas.vivene.inventory.manager.dto.login.LoginResponseDto;
import com.velas.vivene.inventory.manager.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(loginService)).build();
    }

    @Test
    @DisplayName("Deve criar um novo login com sucesso")
    void testCreateLogin() throws Exception {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("test@example.com");
        loginRequestDto.setSenha("password");

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setId(1);
        loginResponseDto.setEmail("test@example.com");

        when(loginService.createLogin(any(LoginRequestDto.class))).thenReturn(loginResponseDto);

        mockMvc.perform(post("/logins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("test@example.com")));

        verify(loginService, times(1)).createLogin(any(LoginRequestDto.class));
    }

    @Test
    @DisplayName("Deve atualizar um login existente com sucesso")
    void testUpdateLogin() throws Exception {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("test@example.com");
        loginRequestDto.setSenha("newpassword");

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setId(1);
        loginResponseDto.setEmail("test@example.com");

        when(loginService.updateLogin(anyInt(), any(LoginRequestDto.class))).thenReturn(loginResponseDto);

        mockMvc.perform(put("/logins/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("test@example.com")));

        verify(loginService, times(1)).updateLogin(anyInt(), any(LoginRequestDto.class));
    }

    @Test
    @DisplayName("Deve deletar um login existente com sucesso")
    void testDeleteLogin() throws Exception {
        doNothing().when(loginService).deleteLogin(anyInt());

        mockMvc.perform(delete("/logins/1"))
                .andExpect(status().isNoContent());

        verify(loginService, times(1)).deleteLogin(anyInt());
    }

    @Test
    @DisplayName("Deve retornar todos os logins com sucesso")
    void testGetAllLogins() throws Exception {
        LoginResponseDto loginResponseDto1 = new LoginResponseDto();
        loginResponseDto1.setId(1);
        loginResponseDto1.setEmail("test1@example.com");

        LoginResponseDto loginResponseDto2 = new LoginResponseDto();
        loginResponseDto2.setId(2);
        loginResponseDto2.setEmail("test2@example.com");

        List<LoginResponseDto> logins = Arrays.asList(loginResponseDto1, loginResponseDto2);

        when(loginService.getAllLogins()).thenReturn(logins);

        mockMvc.perform(get("/logins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].email", is("test1@example.com")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].email", is("test2@example.com")));

        verify(loginService, times(1)).getAllLogins();
    }

    @Test
    @DisplayName("Deve retornar um login pelo ID com sucesso")
    void testGetLoginById() throws Exception {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setId(1);
        loginResponseDto.setEmail("test@example.com");

        when(loginService.getLoginById(anyInt())).thenReturn(loginResponseDto);

        mockMvc.perform(get("/logins/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("test@example.com")));

        verify(loginService, times(1)).getLoginById(anyInt());
    }
}
