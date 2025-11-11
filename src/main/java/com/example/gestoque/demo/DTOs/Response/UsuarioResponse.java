package com.example.gestoque.demo.DTOs.Response;

public class UsuarioResponse {

    private Long id;
    private String nomeCompleto;
    private String email;
    private String perfil;
    private boolean ativo;

    // Construtor
    public UsuarioResponse(Long id, String nomeCompleto, String email, String perfil, boolean ativo) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.perfil = perfil;
        this.ativo = ativo;
    }

    public UsuarioResponse(){}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
