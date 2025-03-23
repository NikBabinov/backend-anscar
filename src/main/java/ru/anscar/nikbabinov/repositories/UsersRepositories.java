package ru.anscar.nikbabinov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.anscar.nikbabinov.entities.Users;

import java.util.List;

@Repository
public interface UsersRepositories extends JpaRepository<Users, Integer> {
    Users findUsersByEmail(String email);

    Boolean existsUsersByEmail(String email);

    @Query("SELECT u FROM Users u")
    List<Users> findAllUsers();
}