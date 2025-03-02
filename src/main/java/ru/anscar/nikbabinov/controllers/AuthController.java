package ru.anscar.nikbabinov.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.services.AuthService;
import ru.anscar.nikbabinov.security.JwtTokenProvider;
import ru.anscar.nikbabinov.entities.User;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserDTO userDTO) {
        try {
            ResponseEntity<?> responseEntity = authService.registerUser(userDTO);
            return getMapResponseEntity(responseEntity);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserDTO userDTO) {
        try {
            ResponseEntity<?> responseEntity = authService.loginUser(userDTO);
            return getMapResponseEntity(responseEntity);
        } catch (Exception e) {
            e.printStackTrace();

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    private ResponseEntity<Map<String, Object>> getMapResponseEntity(ResponseEntity<?> responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            User newUser = (User) responseEntity.getBody();
            String role = newUser.getRole().getAuthority();
            String token = jwtTokenProvider.createToken(newUser.getEmail(), role);
            Map<String, Object> response = new HashMap<>();
            UserDTO responseUser = new UserDTO(newUser.getName(), newUser.getEmail(), null, newUser.getRole().getAuthority());
            response.put("user", responseUser);
            response.put("token", token);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(responseEntity.getStatusCode())
                .body(Map.of("error", responseEntity.getBody()));
    }

}
