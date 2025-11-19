package com.example.gestoque.demo.DTOs.Response;

import java.math.BigDecimal;

public class ProdutoResponse {
    private Long id;
    private String codigoProduto;
    private String nome;
    private String categoria;
    private BigDecimal precoUnitario;
    private Integer quantidadeEstoque;

    public ProdutoResponse() {}

    public ProdutoResponse(Long id, String codigoProduto, String nome, String categoria, BigDecimal precoUnitario, Integer quantidadeEstoque) {
        this.id = id;
        this.codigoProduto = codigoProduto;
        this.nome = nome;
        this.categoria = categoria;
        this.precoUnitario = precoUnitario;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(String codigoProduto) { this.codigoProduto = codigoProduto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
}