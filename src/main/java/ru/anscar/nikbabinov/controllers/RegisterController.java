package ru.anscar.nikbabinov.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.anscar.nikbabinov.dto.UserDTO;
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
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserDTO userDTO) {
        try {
            ResponseEntity<?> responseEntity = registerService.registerUser(userDTO);
            return responseEntityUtil.getMapResponseEntity(responseEntity);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
