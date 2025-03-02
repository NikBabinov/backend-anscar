package ru.anscar.nikbabinov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anscar.nikbabinov.entities.User;

@Repository
public interface UserRepositories extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
    Boolean existsUserByEmail(String email);
}