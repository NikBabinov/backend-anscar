package ru.anscar.nikbabinov.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.repositories.UsersRepositories;
import ru.anscar.nikbabinov.security.UserSecurity;

@Service
public class AuthService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UsersRepositories usersRepositories;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<?> loginUser(UserDTO userDTO) {
        Users users = usersRepositories.findUsersByEmail(userDTO.getEmail());
        if (users != null && passwordEncoder.matches(userDTO.getPassword(), users.getPassword())) {
            logger.info("login users: {}", users);
            return ResponseEntity.ok(users);
        } else {
            logger.info("login users with email: {}, password{} failed", userDTO.getEmail(), userDTO.getPassword());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = getUserByEmail(email);
        if (users == null) {
            logger.info("Users not found: {}", email);
            throw new UsernameNotFoundException("Users not found: " + email);
        }
        return new UserSecurity(users);
    }

    public Users getUserByEmail(String email) {
        return usersRepositories.findUsersByEmail(email);
    }

}
