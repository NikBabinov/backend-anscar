package ru.anscar.nikbabinov.mappers;

import org.springframework.stereotype.Component;
import ru.anscar.nikbabinov.dto.UserStatisticaDTO;
import ru.anscar.nikbabinov.entities.Users;

@Component
public class UserStatisticaMapper {
    public UserStatisticaDTO toDto(Users users) {
        if (users == null || users.getUsersStatistica() == null) {
            return null;
        }
        return new UserStatisticaDTO(
                users.getEmail(),
                users.getName(),
                users.getUsersStatistica().getLevel(),
                users.getUsersStatistica().getCountSolutionTask(),
                users.getUsersStatistica().getSolutionTaskFirstTime()
        );
    }
}
