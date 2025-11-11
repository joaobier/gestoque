package com.example.gestoque.demo.DTOs.Requests;

import java.math.BigDecimal;

public class ProdutoRequest {

    private String codigoProduto;
    private String nome;
    private String categoria;
    private BigDecimal precoUnitario;

    // Construtores
    public ProdutoRequest(String codigoProduto, String nome, String categoria, BigDecimal precoUnitario) {
        this.codigoProduto = codigoProduto;
        this.nome = nome;
        this.categoria = categoria;
        this.precoUnitario = precoUnitario;
    }

    public ProdutoRequest() {}

    //Getters e Setters

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
