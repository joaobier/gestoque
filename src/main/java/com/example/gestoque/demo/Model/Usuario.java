package com.example.gestoque.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto; // Nome completo

    @Column(unique = true, nullable = false)
    private String email; // E-mail (único)

    @Column(nullable = false)
    private String senha; // Senha (armazenada criptografada)

    @Column(nullable = false)
    private String perfil; // Perfil: ADMIN OU OPERADOR

    @Column(nullable = false)
    private boolean ativo = true; // Status: ativo/inativo

    // Construtores
    public Usuario() {}

    public Usuario(String nomeCompleto, String email, String senha, String perfil, boolean ativo) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.ativo = ativo;
    }

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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