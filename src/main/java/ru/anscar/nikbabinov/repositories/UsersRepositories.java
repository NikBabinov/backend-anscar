package ru.anscar.nikbabinov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anscar.nikbabinov.entities.Users;

@Repository
public interface UsersRepositories extends JpaRepository<Users, Integer> {
    Users findUsersByEmail(String email);
    Boolean existsUsersByEmail(String email);
}