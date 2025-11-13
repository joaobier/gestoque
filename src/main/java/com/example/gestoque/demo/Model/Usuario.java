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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil; // Perfil: ADMIN OU OPERADOR

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusUsuario status; // Status: ativo/inativo

    // Construtores
    public Usuario() {}

    public Usuario(String nomeCompleto, String email, String senha, Perfil perfil, StatusUsuario status) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.status = status;
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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public StatusUsuario getStatus() {
        return status;
    }

    public void setStatus(StatusUsuario status){
        this.status = status;
    }


    @Transient
    public boolean isAtivo() {
        return this.status == StatusUsuario.ATIVO;
    }
}