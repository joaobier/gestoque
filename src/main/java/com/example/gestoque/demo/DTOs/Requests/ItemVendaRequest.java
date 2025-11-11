package com.example.gestoque.demo.DTOs.Requests;

public class ItemVendaRequest {
    private Long produtoId;
    private Integer quantidade;

    // Construtores, Getters e Setters...

    public ItemVendaRequest(Long produtoId, Integer quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public ItemVendaRequest() {}

    //Getters e Setters
    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
