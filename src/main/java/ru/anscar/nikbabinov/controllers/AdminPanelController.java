package ru.anscar.nikbabinov.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.services.AdminService;

import java.util.List;

@RestController
@RequestMapping("/user/adminPanel")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AdminPanelController {

    private final AdminService adminService;

    public AdminPanelController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> usersList = adminService.getAllUsers();
        if (usersList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usersList);
    }
}
