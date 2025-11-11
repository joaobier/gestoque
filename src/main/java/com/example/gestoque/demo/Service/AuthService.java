package com.example.gestoque.demo.Service;

import com.example.gestoque.demo.DTOs.Login.LoginRequest;
import com.example.gestoque.demo.DTOs.Login.LoginResponse;
import com.example.gestoque.demo.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioService usuarioService;

    @Autowired
    public AuthService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public LoginResponse authenticate(LoginRequest request) {

        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(request.getEmail());

        if (usuarioOpt.isEmpty()) {
            return null;
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.isAtivo()) {
            return null;
        }

        if (!usuario.getSenha().equals(request.getSenha())) {
            return null;
        }

        return new LoginResponse(usuario.getId(), usuario.getNomeCompleto(), usuario.getPerfil());
    }
}
