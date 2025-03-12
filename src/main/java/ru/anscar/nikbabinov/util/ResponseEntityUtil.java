package ru.anscar.nikbabinov.util;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.security.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseEntityUtil {

    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntityUtil(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<Map<String, Object>> getMapResponseEntity(ResponseEntity<?> responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Users newUsers = (Users) responseEntity.getBody();
            String role = newUsers.getRoles().getRole();
            String token = jwtTokenProvider.createToken(newUsers.getEmail(), role);
            Map<String, Object> response = new HashMap<>();
            UserDTO responseUser = new UserDTO(newUsers.getName(), newUsers.getEmail(), null, newUsers.getRoles().getRole());
            response.put("user", responseUser);
            response.put("token", token);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(responseEntity.getStatusCode())
                .body(Map.of("error", responseEntity.getBody()));
    }
}
