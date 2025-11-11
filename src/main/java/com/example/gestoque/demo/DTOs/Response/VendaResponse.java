package com.example.gestoque.demo.DTOs.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VendaResponse {
    
    private Long id;
    private LocalDateTime dataHora;
    private String nomeUsuarioResponsavel;
    private List<ItemVendaResponse> itens;
    private BigDecimal valorTotal;
    private BigDecimal valorRecebido;
    private BigDecimal troco;

    // Construtor
    public VendaResponse(Long id, LocalDateTime dataHora, String nomeUsuarioResponsavel, List<ItemVendaResponse> itens, BigDecimal valorTotal, BigDecimal valorRecebido, BigDecimal troco) {
        this.id = id;
        this.dataHora = dataHora;
        this.nomeUsuarioResponsavel = nomeUsuarioResponsavel;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.valorRecebido = valorRecebido;
        this.troco = troco;
    }

    public VendaResponse() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public List<ItemVendaResponse> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaResponse> itens) {
        this.itens = itens;
    }

    public String getNomeUsuarioResponsavel() {
        return nomeUsuarioResponsavel;
    }

    public void setNomeUsuarioResponsavel(String nomeUsuarioResponsavel) {
        this.nomeUsuarioResponsavel = nomeUsuarioResponsavel;
    }

    public BigDecimal getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getTroco() {
        return troco;
    }

    public void setTroco(BigDecimal troco) {
        this.troco = troco;
    }
}