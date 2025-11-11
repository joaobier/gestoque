package com.example.gestoque.demo.Service;

import com.example.gestoque.demo.Model.Produto;
import com.example.gestoque.demo.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto) {
        if (produto.getPrecoUnitario() == null || produto.getPrecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço unitário deve ser maior que zero.");
        }

        if (produto.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa.");
        }

        Produto produtoExistente = produtoRepository.findByCodigoProduto(produto.getCodigoProduto());
        if (produtoExistente != null && (produto.getId() == null || !produtoExistente.getId().equals(produto.getId()))) {
            throw new IllegalArgumentException("Código de produto já cadastrado.");
        }

        return produtoRepository.save(produto);
    }

    public void excluir(Long id) {
        produtoRepository.deleteById(id);
    }

    public Produto atualizarEstoque(Produto produto, int quantidadeMovimentada) {
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidadeMovimentada);
        return produtoRepository.save(produto);
    }
}