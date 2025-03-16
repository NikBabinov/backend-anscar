package ru.anscar.nikbabinov.services;

import org.springframework.stereotype.Service;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.repositories.UsersRepositories;

import java.util.List;

@Service
public class AdminService {

    private final UsersRepositories usersRepositories;

    public AdminService(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
    }

    public List<Users> getAllUsers(){
        return usersRepositories.findAllUsers();
    }
}
