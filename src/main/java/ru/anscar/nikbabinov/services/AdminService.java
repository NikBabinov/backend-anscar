package ru.anscar.nikbabinov.services;

import org.springframework.stereotype.Service;
import ru.anscar.nikbabinov.dto.UserStatisticaDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.mappers.UserStatisticaMapper;
import ru.anscar.nikbabinov.repositories.UsersRepositories;

import java.util.List;

@Service
public class AdminService {

    private final UsersRepositories usersRepositories;
    private final UserStatisticaMapper userStatisticaMapper;

    public AdminService(UsersRepositories usersRepositories,
                        UserStatisticaMapper userStatisticaMapper) {
        this.usersRepositories = usersRepositories;
        this.userStatisticaMapper = userStatisticaMapper;
    }

    public List<UserStatisticaDTO> getAllUsersDto() {
        List<Users> allUsersList = usersRepositories.findAll();
        return userStatisticaMapper.usersListToDtoUserList(allUsersList);
    }
}
