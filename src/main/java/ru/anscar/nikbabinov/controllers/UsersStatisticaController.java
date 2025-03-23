package ru.anscar.nikbabinov.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.anscar.nikbabinov.dto.UserStatisticaDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.mappers.UserStatisticaMapper;
import ru.anscar.nikbabinov.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UsersStatisticaController {

    private final UserService userService;
    private final UserStatisticaMapper userStatisticaMapper;

    public UsersStatisticaController(UserService userService, UserStatisticaMapper userStatisticaMapper) {
        this.userService = userService;
        this.userStatisticaMapper = userStatisticaMapper;
    }

    @GetMapping("/statistica")
    public ResponseEntity<UserStatisticaDTO> statistica(@RequestParam String email) {
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Users users = userService.getUserByEmail(email);
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        UserStatisticaDTO userStatisticaDTO = userStatisticaMapper.usersToUserDto(users);
        return ResponseEntity.ok(userStatisticaDTO);
    }
}
