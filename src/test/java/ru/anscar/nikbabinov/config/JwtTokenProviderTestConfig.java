package ru.anscar.nikbabinov.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.anscar.nikbabinov.security.JwtTokenProvider;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class JwtTokenProviderTestConfig {
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return mock(JwtTokenProvider.class);
    }
}

