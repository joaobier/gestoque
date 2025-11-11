package com.example.gestoque.demo.DTOs.Response;

import java.math.BigDecimal;

public class ItemVendaResponse {
    private String nomeProduto;
    private Integer quantidade;
    private BigDecimal precoUnitarioVenda;
    private BigDecimal subtotal;

    // Construtor
    public ItemVendaResponse(String nomeProduto, Integer quantidade, BigDecimal precoUnitarioVenda, BigDecimal subtotal) {
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.precoUnitarioVenda = precoUnitarioVenda;
        this.subtotal = subtotal;
    }

    public ItemVendaResponse() {}

    // Getters e Setters
    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitarioVenda() {
        return precoUnitarioVenda;
    }

    public void setPrecoUnitarioVenda(BigDecimal precoUnitarioVenda) {
        this.precoUnitarioVenda = precoUnitarioVenda;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
