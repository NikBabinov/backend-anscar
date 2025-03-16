package ru.anscar.nikbabinov.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.repositories.UsersRepositories;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceConfigMockitoTest {

    @Mock
    private UsersRepositories usersRepositories;

    @InjectMocks
    private RegisterService registerService;

    @Test
    void givenUser_whenRegistration_thenRegistrationDate() {
        Users users = getTestUser();
        UserDTO userDTO = new UserDTO();
        userDTO.setName(users.getName());
        userDTO.setEmail(users.getEmail());
        userDTO.setPassword(users.getPassword());

        when(usersRepositories.save(any(Users.class))).thenAnswer(invocation -> {
            Users user = invocation.getArgument(0, Users.class);
            user.setUserCreateDate(new Date());
            return user;
        });


        ResponseEntity<?> responseEntity = registerService.registerUser(userDTO);
        Users usersRequest = (Users) responseEntity.getBody();

        assertThat(usersRequest.getUserCreateDate()).isNotNull();
    }


    private Users getTestUser() {
        return Users.baseBuilder()
                .name("Nikolay")
                .email("nik777@gmail.com")
                .password("password")
                .build();
    }
}