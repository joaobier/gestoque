package com.example.gestoque.demo.Service;

import com.example.gestoque.demo.DTOs.Login.LoginRequest;
import com.example.gestoque.demo.DTOs.Login.LoginResponse;
import com.example.gestoque.demo.DTOs.Requests.UsuarioRequest;
import com.example.gestoque.demo.DTOs.Response.UsuarioResponse;
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

    public UsuarioResponse criarUsuario(UsuarioRequest request) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNomeCompleto(request.getNomeCompleto());
        novoUsuario.setEmail(request.getEmail());
        novoUsuario.setSenha(request.getSenha());

        novoUsuario.setPerfil(request.getPerfil() != null ? request.getPerfil().toUpperCase() : "OPERADOR");
        novoUsuario.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);

        Usuario usuarioSalvo;
        try {
            usuarioSalvo = usuarioService.salvar(novoUsuario);
        } catch (IllegalArgumentException e) {
            // Repassa a exceção de regra de negócio (ex: e-mail já existe)
            throw new IllegalArgumentException(e.getMessage());
        }

        return new UsuarioResponse(
                usuarioSalvo.getId(),
                usuarioSalvo.getNomeCompleto(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getPerfil(),
                usuarioSalvo.isAtivo()
        );
    }
}
