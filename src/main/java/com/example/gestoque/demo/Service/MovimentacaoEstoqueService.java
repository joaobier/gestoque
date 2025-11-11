package com.example.gestoque.demo.Service;

import com.example.gestoque.demo.Model.MovimentacaoEstoque;
import com.example.gestoque.demo.Model.Produto;
import com.example.gestoque.demo.Repository.MovimentacaoEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    private final ProdutoService produtoService;

    @Autowired
    public MovimentacaoEstoqueService(MovimentacaoEstoqueRepository movimentacaoEstoqueRepository, ProdutoService produtoService) {
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
        this.produtoService = produtoService;
    }

    public List<MovimentacaoEstoque> listarTodos() {
        return movimentacaoEstoqueRepository.findAll();
    }

    @Transactional
    public MovimentacaoEstoque registrarMovimentacao(MovimentacaoEstoque movimentacao) {
        Produto produto = movimentacao.getProduto();

        if (produto == null || produto.getId() == null) {
            throw new IllegalArgumentException("Produto inválido para a movimentação.");
        }

        Optional<Produto> produtoOpt = produtoService.buscarPorId(produto.getId());
        if (!produtoOpt.isPresent()) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }
        Produto produtoAtualizado = produtoOpt.get();

        int quantidade = movimentacao.getQuantidade();
        String tipo = movimentacao.getTipo();

        if ("ENTRADA".equals(tipo) || "AJUSTE".equals(tipo)) {
            int novoEstoque = produtoAtualizado.getQuantidadeEstoque() + quantidade;

            if (novoEstoque < 0) {
                throw new IllegalArgumentException("O ajuste resultaria em estoque negativo. Saldo atual: " + produtoAtualizado.getQuantidadeEstoque());
            }

            produtoService.atualizarEstoque(produtoAtualizado, quantidade);

            return movimentacaoEstoqueRepository.save(movimentacao);
        } else {
            throw new IllegalArgumentException("Tipo de movimentação inválido. Use ENTRADA ou AJUSTE.");
        }
    }
}