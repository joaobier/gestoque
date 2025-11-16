package com.example.gestoque.demo.Service;

import com.example.gestoque.demo.DTOs.Requests.ProdutoRequest;
import com.example.gestoque.demo.DTOs.Response.ProdutoResponse;
import com.example.gestoque.demo.Model.Produto;
import com.example.gestoque.demo.Repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
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

    private ProdutoResponse toResponseDTO(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getCodigoProduto(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getQuantidadeEstoque(),
                produto.getPrecoUnitario()
        );
    }

    public List<ProdutoResponse> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProdutoResponse buscarPorIdResponse(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));
        return toResponseDTO(produto);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public ProdutoResponse criarProduto(ProdutoRequest request) {
        validarProduto(request);

        Produto produtoExistente = produtoRepository.findByCodigoProduto(request.getCodigoProduto());
        if (produtoExistente != null) {
            throw new IllegalArgumentException("Código de produto já cadastrado.");
        }

        Produto novoProduto = new Produto();
        novoProduto.setQuantidadeEstoque(0);
        mapearDtoParaEntidade(request, novoProduto);

        Produto produtoSalvo = produtoRepository.save(novoProduto);
        return toResponseDTO(produtoSalvo);
    }

    public ProdutoResponse atualizarProduto(Long id, ProdutoRequest request) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        validarProduto(request);

        Produto produtoPorCodigo = produtoRepository.findByCodigoProduto(request.getCodigoProduto());
        if (produtoPorCodigo != null && !produtoPorCodigo.getId().equals(id)) {
            throw new IllegalArgumentException("Código de produto já pertence a outro item.");
        }

        mapearDtoParaEntidade(request, produtoExistente);

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        return toResponseDTO(produtoAtualizado);
    }

    public void excluir(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    private void mapearDtoParaEntidade(ProdutoRequest request, Produto produto) {
        produto.setCodigoProduto(request.getCodigoProduto());
        produto.setNome(request.getNome());
        produto.setCategoria(request.getCategoria());
        produto.setPrecoUnitario(request.getPrecoUnitario());
    }

    private void validarProduto(ProdutoRequest request) {
        if (request.getPrecoUnitario() == null || request.getPrecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço unitário deve ser maior que zero.");
        }
        if (request.getCodigoProduto() == null || request.getCodigoProduto().trim().isEmpty()) {
            throw new IllegalArgumentException("O código do produto é obrigatório.");
        }
    }

    public Produto atualizarEstoque(Produto produto, int quantidadeMovimentada) {
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidadeMovimentada);
        return produtoRepository.save(produto);
    }
}