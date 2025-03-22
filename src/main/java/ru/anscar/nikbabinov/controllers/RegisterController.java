package ru.anscar.nikbabinov.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.services.RegisterService;
import ru.anscar.nikbabinov.util.ResponseEntityUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/register")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class RegisterController {

    private final RegisterService registerService;
    private final ResponseEntityUtil responseEntityUtil;

    public RegisterController(RegisterService registerService, ResponseEntityUtil responseEntityUtil) {
        this.registerService = registerService;
        this.responseEntityUtil = responseEntityUtil;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        try {
            Users saveUsers = registerService.registerUser(userDTO);
            if (saveUsers != null) {
                return responseEntityUtil.getMapResponseEntity(saveUsers, response);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
