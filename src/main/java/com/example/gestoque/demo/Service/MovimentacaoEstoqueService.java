package com.example.gestoque.demo.Service;

import com.example.gestoque.demo.DTOs.Requests.MovimentacaoRequest;
import com.example.gestoque.demo.DTOs.Response.MovimentacaoResponse;
import com.example.gestoque.demo.Model.MovimentacaoEstoque;
import com.example.gestoque.demo.Model.Produto;
import com.example.gestoque.demo.Model.TipoMovimentacao;
import com.example.gestoque.demo.Repository.MovimentacaoEstoqueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    private final ProdutoService produtoService;

    @Autowired
    public MovimentacaoEstoqueService(MovimentacaoEstoqueRepository movimentacaoEstoqueRepository, ProdutoService produtoService) {
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
        this.produtoService = produtoService;
    }

    private MovimentacaoResponse toResponseDTO(MovimentacaoEstoque mov) {
        return new MovimentacaoResponse(
                mov.getId(),
                mov.getProduto().getId(),
                mov.getProduto().getNome(),
                mov.getDataHora(),
                mov.getTipo().name(),
                mov.getQuantidade(),
                mov.getMotivo()
        );
    }

    public List<MovimentacaoResponse> listarHistorico() {
        return movimentacaoEstoqueRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MovimentacaoResponse registrarMovimentacao(MovimentacaoRequest request) {
        TipoMovimentacao tipo;
        try {
            tipo = TipoMovimentacao.valueOf(request.getTipo().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Tipo de movimentação inválido. Use ENTRADA ou AJUSTE.");
        }

        Produto produto = produtoService.buscarPorId(request.getProdutoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + request.getProdutoId()));

        int quantidade = request.getQuantidade();

        if (tipo == TipoMovimentacao.ENTRADA && quantidade <= 0) {
            throw new IllegalArgumentException("ENTRADA deve ter quantidade positiva.");
        }
        if (tipo == TipoMovimentacao.AJUSTE && quantidade == 0) {
            throw new IllegalArgumentException("AJUSTE deve ter quantidade diferente de zero.");
        }

        int novoEstoque = produto.getQuantidadeEstoque() + quantidade;
        if (novoEstoque < 0) {
            throw new IllegalArgumentException("O ajuste resultaria em estoque negativo. Saldo atual: " + produto.getQuantidadeEstoque());
        }

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setProduto(produto);
        movimentacao.setTipo(tipo);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setMotivo(request.getMotivo());

        MovimentacaoEstoque movSalva = movimentacaoEstoqueRepository.save(movimentacao);

        produtoService.atualizarEstoque(produto, quantidade);

        return toResponseDTO(movSalva);
    }
}