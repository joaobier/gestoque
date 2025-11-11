package com.example.gestoque.demo.DTOs.Response;

import java.time.LocalDateTime;

public class MovimentacaoResponse {

    private Long id;
    private Long produtoId;
    private String nomeProduto; // Para exibição no Front-end
    private LocalDateTime dataHora;
    private String tipo;
    private Integer quantidade;
    private String motivo;

    // Construtor
    public MovimentacaoResponse(Long id, Long produtoId, String nomeProduto, LocalDateTime dataHora, String tipo, Integer quantidade, String motivo) {
        this.id = id;
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.dataHora = dataHora;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.motivo = motivo;
    }

    public MovimentacaoResponse() {}

    // Getters e Setters...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
