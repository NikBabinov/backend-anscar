package ru.anscar.nikbabinov.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
import ru.anscar.nikbabinov.services.AdminService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.*;

@Import({TestSecurityConfig.class, JwtTokenProviderTestConfig.class})
@WebMvcTest(controllers = AdminPanelController.class)
@ActiveProfiles("test")
public class AdminPanelControllerRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getAllUsers_Statistica_whenAccessAdmin_thenReturn200() throws Exception {
        List<UserStatisticaDTO> usersListStatisticaDto = new ArrayList<>();

        UserStatisticaDTO userStatisticaDTO = getUserStatisticaDTO();

        usersListStatisticaDto.add(userStatisticaDTO);
        when(adminService.getAllUsersDto()).thenReturn(usersListStatisticaDto);
        mockMvc.perform(get("/user/adminPanel")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void getAllUsers_Statistica_whenAccessUser_thenReturn403() throws Exception {
        List<UserStatisticaDTO> usersListStatisticaDto = new ArrayList<>();

        UserStatisticaDTO userStatisticaDTO = getUserStatisticaDTO();

        usersListStatisticaDto.add(userStatisticaDTO);
        when(adminService.getAllUsersDto()).thenReturn(usersListStatisticaDto);
        mockMvc.perform(get("/user/adminPanel")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getUser_whenAccessAdmin_thenReturnListUsers() throws Exception {
        List<UserStatisticaDTO> usersListStatisticaDto = new ArrayList<>();

        UserStatisticaDTO userStatisticaDTO = getUserStatisticaDTO();

        usersListStatisticaDto.add(userStatisticaDTO);

        when(adminService.getAllUsersDto()).thenReturn(usersListStatisticaDto);

        String userListAsString = objectMapper.writeValueAsString(usersListStatisticaDto);

        MvcResult mvcResult = mockMvc.perform(get("/user/adminPanel")).andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(userListAsString);
    }

    private UserStatisticaDTO getUserStatisticaDTO() {
        return new UserStatisticaDTO(1L
                , "test"
                , "test@mail.ru"
                , 1
                , 1
                , 1
                , new Date()
                , new Date()
                , "Role");
    }
}
