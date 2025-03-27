package ru.anscar.nikbabinov.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.anscar.nikbabinov.security.entity.UserSecurity;
import ru.anscar.nikbabinov.security.service.AuthService;

@Configuration
public class UserManagerConfig {

    private final AuthService authService;

    public UserManagerConfig(AuthService authService) {
        this.authService = authService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            try {
                return new UserSecurity(authService.getUserByEmail(email));
            } catch (RuntimeException e) {
                throw new UsernameNotFoundException("Users not found: " + email);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
