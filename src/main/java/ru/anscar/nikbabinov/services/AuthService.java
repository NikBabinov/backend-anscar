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
import org.springframework.transaction.annotation.Transactional;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.entities.User;
import ru.anscar.nikbabinov.security.UserSecurity;
import ru.anscar.nikbabinov.repositories.UserRepositories;

@Service
public class AuthService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepositories userRepositories) {
        this.userRepositories = userRepositories;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User saveUser(User user) {
        logger.info("Saving user: {}", user);
        return userRepositories.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepositories.findUserByEmail(email);
    }

    @Transactional
    public ResponseEntity<?> registerUser(UserDTO userDTO) {
        if (!isUserEmailUnique(userDTO.getEmail())) {
            logger.info("Email already in use: {}", userDTO.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email is already in use!");
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepositories.save(user);
        logger.info("register user: {}", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Transactional
    public ResponseEntity<?> loginUser(UserDTO userDTO) {
        User user = userRepositories.findUserByEmail(userDTO.getEmail());
        if (user != null && passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            logger.info("login user: {}", user);
            return ResponseEntity.ok(user);
        } else {
            logger.info("login user with email: {}, password{} failed", userDTO.getEmail(), userDTO.getPassword());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }


    public boolean isUserEmailUnique(String userEmail) {
        return !userRepositories.existsUserByEmail(userEmail);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepositories.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        return new UserSecurity(user);
    }
}
