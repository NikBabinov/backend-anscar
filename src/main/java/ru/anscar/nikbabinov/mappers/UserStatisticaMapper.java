package ru.anscar.nikbabinov.mappers;

import org.springframework.stereotype.Component;
import ru.anscar.nikbabinov.dto.UserStatisticaDTO;
import ru.anscar.nikbabinov.entities.Users;

import java.util.List;

@Component
public class UserStatisticaMapper {

    public UserStatisticaDTO usersToUserDto(Users users) {
        if (users == null || users.getUsersStatistica() == null) {
            return null;
        }
        return new UserStatisticaDTO(
                users.getId(),
                users.getName(),
                users.getEmail(),
                users.getUsersStatistica().getLevel(),
                users.getUsersStatistica().getCountSolutionTask(),
                users.getUsersStatistica().getSolutionTaskFirstTime(),
                users.getUserCreateDate(),
                users.getUserUpdateDate(),
                users.getRoles().getRole()
        );
    }

    public List<UserStatisticaDTO> usersListToDtoUserList(List<Users> usersList) {
        if (usersList == null || usersList.isEmpty()) {
            return null;
        }
        return usersList.stream()
                .map(this::usersToUserDto)
                .toList();
    }

}
