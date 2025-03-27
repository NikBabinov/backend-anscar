package ru.anscar.nikbabinov.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.security.service.AuthService;
import ru.anscar.nikbabinov.util.ResponseEntityUtil;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/auth/login")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;
    private final ResponseEntityUtil responseEntityUtil;

    public AuthController(AuthService authService, ResponseEntityUtil responseEntityUtil) {
        this.authService = authService;
        this.responseEntityUtil = responseEntityUtil;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        try {
            Users autUser = authService.loginUser(userDTO);
            return responseEntityUtil.getMapResponseEntity(autUser, response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


}
