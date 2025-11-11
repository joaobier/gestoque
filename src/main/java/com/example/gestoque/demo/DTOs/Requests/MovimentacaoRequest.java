package com.example.gestoque.demo.DTOs.Requests;

public class MovimentacaoRequest {

    private Long produtoId;
    private String tipo; // ENTRADA ou AJUSTE
    private Integer quantidade; // Positivo ou negativo
    private String motivo;

    //Construtores
    public MovimentacaoRequest(Long produtoId, String tipo, Integer quantidade, String motivo) {
        this.produtoId = produtoId;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.motivo = motivo;
    }

    public MovimentacaoRequest() {}

    //Getters e Setters
    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
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