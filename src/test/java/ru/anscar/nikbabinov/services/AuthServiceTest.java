package ru.anscar.nikbabinov.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.anscar.nikbabinov.entities.User;
import ru.anscar.nikbabinov.repositories.UserRepositories;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    private final UserRepositories userRepositories = mock(UserRepositories.class);
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userRepositories);
    }


    @Test
    void givenUser_whenSaved_thenHashRegistration() {
        User user = User.baseBuilder()
                .email("nik@mail.ru")
                .name("nik")
                .password("123")
                .build();

        when(userRepositories.save(any(User.class))).then(returnsFirstArg());
        User saveUser = authService.saveUser(user);
        Assertions.assertThat(saveUser.getName()).isNotNull();
    }
}