package com.example.gestoque.demo.Service;

import com.example.gestoque.demo.DTOs.Requests.UsuarioRequest;
import com.example.gestoque.demo.DTOs.Response.UsuarioResponse;
import com.example.gestoque.demo.Model.Perfil;
import com.example.gestoque.demo.Model.StatusUsuario;
import com.example.gestoque.demo.Model.Usuario;
import com.example.gestoque.demo.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private UsuarioResponse toResponseDTO(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNomeCompleto(),
                usuario.getEmail(),
                usuario.getPerfil().name(),
                usuario.isAtivo()
        );
    }

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));
        return toResponseDTO(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioResponse criarUsuario(UsuarioRequest request) {
        Usuario novoUsuario = new Usuario();
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        this.mapearDtoParaEntidade(request, novoUsuario);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return toResponseDTO(usuarioSalvo);
    }

    public UsuarioResponse atualizarUsuario(Long id, UsuarioRequest request) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));

        Optional<Usuario> outroUsuarioComEmail = usuarioRepository.findByEmail(request.getEmail());
        if (outroUsuarioComEmail.isPresent() && !outroUsuarioComEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("E-mail já pertence a outro usuário.");
        }

        this.mapearDtoParaEntidade(request, usuarioExistente);

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return toResponseDTO(usuarioAtualizado);
    }

    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == null && usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        return usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private void mapearDtoParaEntidade(UsuarioRequest request, Usuario usuario) {
        usuario.setNomeCompleto(request.getNomeCompleto());
        usuario.setEmail(request.getEmail());

        if (request.getSenha() != null && !request.getSenha().isEmpty()) {
            usuario.setSenha(request.getSenha());
        }

        try {
            usuario.setPerfil(Perfil.valueOf(request.getPerfil().toUpperCase()));
        } catch (Exception e) {
            usuario.setPerfil(Perfil.OPERADOR);
        }

        if (request.getAtivo() != null && !request.getAtivo()) {
            usuario.setStatus(StatusUsuario.INATIVO);
        } else {
            usuario.setStatus(StatusUsuario.ATIVO);
        }
    }

}