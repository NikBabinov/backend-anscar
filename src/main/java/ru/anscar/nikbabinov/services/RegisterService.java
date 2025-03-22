package ru.anscar.nikbabinov.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.anscar.nikbabinov.controllers.RegisterController;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.repositories.UsersRepositories;

@Service
public class RegisterService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final UsersRepositories usersRepositories;
    private PasswordEncoder passwordEncoder;

    public RegisterService(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Transactional
    public Users registerUser(UserDTO userDTO) {
        if (!isUserEmailUnique(userDTO.getEmail())) {
            return null;
        }
        Users users = new Users();
        users.setName(userDTO.getName());
        users.setEmail(userDTO.getEmail());
        users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Users savedUser = usersRepositories.save(users);
        logger.info("register users: {}", users);
        return savedUser;
    }

    public boolean isUserEmailUnique(String userEmail) {
        return !usersRepositories.existsUsersByEmail(userEmail);
    }

}
