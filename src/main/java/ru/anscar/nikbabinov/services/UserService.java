package ru.anscar.nikbabinov.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.repositories.UsersRepositories;

@Service
public class UserService {
    private final UsersRepositories repositories;

    public UserService(UsersRepositories repositories) {
        this.repositories = repositories;
    }

    @Transactional
    public Users getUserByEmail(String email) {
        return repositories.findUsersByEmail(email);
    }
}
