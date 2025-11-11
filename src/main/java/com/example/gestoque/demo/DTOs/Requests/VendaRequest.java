package com.example.gestoque.demo.DTOs.Requests;

import java.math.BigDecimal;
import java.util.List;

public class VendaRequest {

    private Long usuarioResponsavelId;
    private List<ItemVendaRequest> itens;
    private BigDecimal valorRecebido;

    // Construtores
    public VendaRequest(Long usuarioResponsavelId, List<ItemVendaRequest> itens, BigDecimal valorRecebido) {
        this.usuarioResponsavelId = usuarioResponsavelId;
        this.itens = itens;
        this.valorRecebido = valorRecebido;
    }

    public VendaRequest() {}

    //Getters e Setters
    public Long getUsuarioResponsavelId() {
        return usuarioResponsavelId;
    }

    public void setUsuarioResponsavelId(Long usuarioResponsavelId) {
        this.usuarioResponsavelId = usuarioResponsavelId;
    }

    public List<ItemVendaRequest> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaRequest> itens) {
        this.itens = itens;
    }

    public BigDecimal getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }
}
