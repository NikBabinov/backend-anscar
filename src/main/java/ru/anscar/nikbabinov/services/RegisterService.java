package ru.anscar.nikbabinov.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.exceptions.UserAlreadyExistsException;
import ru.anscar.nikbabinov.repositories.UsersRepositories;

@Service
public class RegisterService {
    private final UsersRepositories usersRepositories;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Transactional
    public Users registerUser(UserDTO userDTO) {
        if (!isUserEmailUnique(userDTO.getEmail())) {
            throw new UserAlreadyExistsException(
                    "User with email " + userDTO.getEmail() + " already exists."
            );
        }
        Users users = new Users();
        users.setName(userDTO.getName());
        users.setEmail(userDTO.getEmail());
        users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return usersRepositories.save(users);
    }

    public boolean isUserEmailUnique(String userEmail) {
        return !usersRepositories.existsUsersByEmail(userEmail);
    }

}
