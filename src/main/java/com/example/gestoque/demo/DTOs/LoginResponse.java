package com.example.gestoque.demo.DTOs;

public class LoginResponse {
    private Long id;
    private String nome;
    private String perfil;

    public LoginResponse(Long id, String nome, String perfil) {
        this.id = id;
        this.nome = nome;
        this.perfil = perfil;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
