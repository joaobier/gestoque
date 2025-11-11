package com.example.gestoque.demo.Controller;

import com.example.gestoque.demo.DTOs.Login.LoginRequest;
import com.example.gestoque.demo.DTOs.Login.LoginResponse;
import com.example.gestoque.demo.DTOs.Requests.UsuarioRequest;
import com.example.gestoque.demo.DTOs.Response.UsuarioResponse;
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

    @PostMapping("/login/criar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioRequest request) {
        try {
            UsuarioResponse response = authService.criarUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}