package ru.anscar.nikbabinov.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.anscar.nikbabinov.config.JwtTokenProviderTestConfig;
import ru.anscar.nikbabinov.config.TestSecurityConfig;
import ru.anscar.nikbabinov.dto.UserStatisticaDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.mappers.UserStatisticaMapper;
import ru.anscar.nikbabinov.services.UserService;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({TestSecurityConfig.class, JwtTokenProviderTestConfig.class})
@WebMvcTest(controllers = UsersStatisticaController.class)
@ActiveProfiles("test")
@WithMockUser
public class UsersStatisticaControllerRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserStatisticaMapper userStatisticaMapper;

    @MockBean
    private UserService userService;

    @Test
    void statistica_whenValidEmail_thenReturns200() throws Exception {
        Users user = new Users("nik", "test@mail.ru", "password");
        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(userStatisticaMapper.toDto(user)).thenReturn(new UserStatisticaDTO());

        mockMvc.perform(get("/user/statistica")
                        .param("email", user.getEmail()))
                .andExpect(status().isOk());
    }

    @Test
    void statistica_whenNullEmail_thenReturns400() throws Exception {
        mockMvc.perform(get("/user/statistica")
                        .param("email", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void statistica_whenNotRegisterUserEmail_thenReturns404() throws Exception {
        Users user = new Users("nik", "test@mail.ru", "password");
        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(userStatisticaMapper.toDto(user)).thenReturn(new UserStatisticaDTO());

        mockMvc.perform(get("/user/statistica")
                        .param("email", "notRegisterUser@mail.ru"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void statistica_whenValidEmail_thenMapToBusinessModel() throws Exception {
        Users user = new Users("nik", "test@mail.ru", "password");
        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

        mockMvc.perform(get("/user/statistica")
                .param("email", user.getEmail()));

        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
        verify(userStatisticaMapper, times(1)).toDto(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("nik");
    }

    @Test
    void statistica_whenValidInput_thenReturnResponseEntityUserStatisticaDTO() throws Exception {
        Users user = new Users("nik", "test@mail.ru", "password");
        UserStatisticaDTO userStatisticaDTO = new UserStatisticaDTO(
                user.getName(),
                user.getEmail(),
                1,
                1,
                1
        );

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(userStatisticaMapper.toDto(user)).thenReturn(userStatisticaDTO);

        MvcResult mvcResult = mockMvc.perform(get("/user/statistica")
                        .param("email", user.getEmail()))
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(userStatisticaDTO)
        );
    }
}
