package com.example.gestoque.demo.DTOs.Response;

import java.math.BigDecimal;
import java.util.List;

public class RelatorioVendasResponse {

    private int totalDeVendas;
    private int totalItensVendidos;
    private BigDecimal valorTotalVendido;

    private List<VendaResponse> vendas;

    public RelatorioVendasResponse(List<VendaResponse> vendas) {
        this.vendas = vendas;
        this.totalDeVendas = vendas.size();
        this.valorTotalVendido = vendas.stream()
                .map(VendaResponse::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalItensVendidos = vendas.stream()
                .flatMap(venda -> venda.getItens().stream())
                .mapToInt(ItemVendaResponse::getQuantidade)
                .sum();
    }

    public int getTotalDeVendas() {
        return totalDeVendas;
    }

    public void setTotalDeVendas(int totalDeVendas) {
        this.totalDeVendas = totalDeVendas;
    }

    public int getTotalItensVendidos() {
        return totalItensVendidos;
    }

    public void setTotalItensVendidos(int totalItensVendidos) {
        this.totalItensVendidos = totalItensVendidos;
    }

    public BigDecimal getValorTotalVendido() {
        return valorTotalVendido;
    }

    public void setValorTotalVendido(BigDecimal valorTotalVendido) {
        this.valorTotalVendido = valorTotalVendido;
    }

    public List<VendaResponse> getVendas() {
        return vendas;
    }

    public void setVendas(List<VendaResponse> vendas) {
        this.vendas = vendas;
    }
}