package ru.anscar.nikbabinov.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.entities.Users;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class RegisterServiceFieldInjectionTest {


    @Autowired
    private RegisterService registerService;

    @Test
    void givenUser_whenRegisterUser_thenRegistrationCreateDate() {
        Users users = getTestUser();
        UserDTO userDTO = new UserDTO();
        userDTO.setName(users.getName());
        userDTO.setEmail(users.getEmail());
        userDTO.setPassword(users.getPassword());

        Users saveUser = registerService.registerUser(userDTO);
        assertThat(saveUser.getUserCreateDate()).isNotNull();
    }

    private Users getTestUser() {
        return Users.baseBuilder()
                .name("Nikolay")
                .email("nik777@gmail.com")
                .password("password")
                .build();
    }
}