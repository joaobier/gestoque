package com.example.gestoque.demo.Controller;

import com.example.gestoque.demo.DTOs.LoginRequest;
import com.example.gestoque.demo.DTOs.LoginResponse;
import com.example.gestoque.demo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        LoginResponse response = authService.authenticate(request);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}