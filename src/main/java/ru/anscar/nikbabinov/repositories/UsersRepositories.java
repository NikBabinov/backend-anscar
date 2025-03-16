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

    @Query(value = """
                 SELECT
                     u.id, u.name, u.email, u.user_create_date, u.user_update_date,
                     r.role,
                     us.level, us.count_solution_task, us.solution_task_first_time
                 FROM users u
                 LEFT JOIN anscar.roles r on u.roles_id = r.id
                 LEFT JOIN anscar.users_statistica us on us.id = u.users_statistica_id
            """, nativeQuery = true)
    List<Users> findAllUsers();
}