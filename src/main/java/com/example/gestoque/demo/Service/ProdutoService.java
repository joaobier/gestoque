package com.example.gestoque.demo.Service;

import com.example.gestoque.demo.DTOs.Requests.ProdutoRequest;
import com.example.gestoque.demo.DTOs.Response.ProdutoResponse;
import com.example.gestoque.demo.Model.Produto;
import com.example.gestoque.demo.Repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoResponse> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProdutoResponse buscarPorIdResponse(Long id) {
        return toResponseDTO(buscarEntidadePorId(id));
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    private Produto buscarEntidadePorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado: " + id));
    }

    @Transactional
    public ProdutoResponse criarProduto(ProdutoRequest request) {
        Produto produto = new Produto();
        produto.setCodigoProduto(request.getCodigoProduto());
        produto.setNome(request.getNome());
        produto.setCategoria(request.getCategoria());
        produto.setPrecoUnitario(request.getPrecoUnitario());

        produto.setQuantidadeEstoque(request.getQuantidadeEstoque() != null ? request.getQuantidadeEstoque() : 0);

        produto = produtoRepository.save(produto);
        return toResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponse atualizarProduto(Long id, ProdutoRequest request) {
        Produto produto = buscarEntidadePorId(id);

        produto.setCodigoProduto(request.getCodigoProduto());
        produto.setNome(request.getNome());
        produto.setCategoria(request.getCategoria());
        produto.setPrecoUnitario(request.getPrecoUnitario());
        produto = produtoRepository.save(produto);
        return toResponseDTO(produto);
    }

    @Transactional
    public void excluir(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    @Transactional
    public void atualizarEstoque(Produto produto, int quantidade) {
        int novaQuantidade = produto.getQuantidadeEstoque() + quantidade;
        if (novaQuantidade < 0) {
            throw new IllegalArgumentException("Estoque insuficiente.");
        }
        produto.setQuantidadeEstoque(novaQuantidade);
        produtoRepository.save(produto);
    }

    private ProdutoResponse toResponseDTO(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getCodigoProduto(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getPrecoUnitario(),
                produto.getQuantidadeEstoque()
        );
    }
}