package ru.anscar.nikbabinov.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.repositories.UsersRepositories;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RegisterServiceWithManualConfigMockitoTest {

    private final UsersRepositories usersRepositories = Mockito.mock(UsersRepositories.class);
    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        registerService = new RegisterService(usersRepositories);
    }

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

