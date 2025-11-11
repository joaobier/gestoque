package com.example.gestoque.demo.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoesestoque")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relação com Produto(varias movimentações podem se relacionar com um produto)
    @ManyToOne
    @JoinColumn(name = "produtoid", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private LocalDateTime dataHora; // data e hora

    @Column(nullable = false)
    private String tipo; // Tipo: ENTRADA, SAIDA

    @Column(nullable = false)
    private Integer quantidade; // Quantidade de itens movimentados

    // Construtores
    public MovimentacaoEstoque() {
        this.dataHora = LocalDateTime.now();
    }

    public MovimentacaoEstoque(Long id, Produto produto, LocalDateTime dataHora, String tipo, Integer quantidade) {
        this.id = id;
        this.produto = produto;
        this.dataHora = dataHora;
        this.tipo = tipo;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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
}