package ru.anscar.nikbabinov.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.services.AuthService;
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
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserDTO userDTO) {
        try {
            ResponseEntity<?> responseEntity = authService.loginUser(userDTO);
            return responseEntityUtil.getMapResponseEntity(responseEntity);
        } catch (Exception e) {
            e.printStackTrace();

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

}
