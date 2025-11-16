package com.example.gestoque.demo.Service;

import com.example.gestoque.demo.DTOs.Requests.ItemVendaRequest;
import com.example.gestoque.demo.DTOs.Requests.VendaRequest;
import com.example.gestoque.demo.DTOs.Response.ItemVendaResponse;
import com.example.gestoque.demo.DTOs.Response.VendaResponse;
import com.example.gestoque.demo.Model.*;
import com.example.gestoque.demo.Repository.VendaRepository;
import com.example.gestoque.demo.Repository.ItemVendaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ItemVendaRepository itemVendaRepository;
    private final ProdutoService produtoService;
    private final UsuarioService usuarioService;

    @Autowired
    public VendaService(VendaRepository vendaRepository, ItemVendaRepository itemVendaRepository, ProdutoService produtoService, UsuarioService usuarioService) {
        this.vendaRepository = vendaRepository;
        this.itemVendaRepository = itemVendaRepository;
        this.produtoService = produtoService;
        this.usuarioService = usuarioService;
    }

    private VendaResponse toResponseDTO(Venda venda) {
        List<ItemVendaResponse> itensResponse = venda.getItens().stream()
                .map(item -> new ItemVendaResponse(
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPrecoUnitarioVenda(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        return new VendaResponse(
                venda.getId(),
                venda.getDataHora(),
                venda.getUsuarioResponsavel().getNomeCompleto(),
                itensResponse,
                venda.getValorTotal(),
                venda.getValorRecebido(),
                venda.getTroco()
        );
    }

    public List<Venda> listarTodos() {
        return vendaRepository.findAll();
    }

    public List<Venda> buscarPorUsuario(Long usuarioId) {
        return vendaRepository.findByUsuarioResponsavelId(usuarioId);
    }

    @Transactional
    public VendaResponse registrarVenda(VendaRequest request) {

        Usuario usuario = usuarioService.buscarPorEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + request.getUsuarioResponsavelId()));

        Venda novaVenda = new Venda();
        novaVenda.setUsuarioResponsavel(usuario);
        novaVenda.setValorRecebido(request.getValorRecebido());
        novaVenda.setDataHora(LocalDateTime.now());
        novaVenda.setItens(new ArrayList<>());

        BigDecimal totalVenda = BigDecimal.ZERO;

        if (request.getItens() == null || request.getItens().isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter pelo menos um item.");
        }

        for (ItemVendaRequest itemRequest : request.getItens()) {
            Produto produto = produtoService.buscarPorId(itemRequest.getProdutoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + itemRequest.getProdutoId() + " não encontrado."));

            int quantidadeVendida = itemRequest.getQuantidade();

            if (quantidadeVendida <= 0 || quantidadeVendida > produto.getQuantidadeEstoque()) {
                throw new IllegalArgumentException("Quantidade inválida ou insuficiente em estoque para o produto: " + produto.getNome());
            }

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(quantidadeVendida);
            itemVenda.setPrecoUnitarioVenda(produto.getPrecoUnitario());

            BigDecimal subtotal = produto.getPrecoUnitario().multiply(new BigDecimal(quantidadeVendida));
            itemVenda.setSubtotal(subtotal);
            itemVenda.setVenda(novaVenda);

            novaVenda.getItens().add(itemVenda);
            totalVenda = totalVenda.add(subtotal);

            produtoService.atualizarEstoque(produto, -quantidadeVendida);
        }

        if (request.getValorRecebido() == null || request.getValorRecebido().compareTo(totalVenda) < 0) {
            throw new IllegalArgumentException("O valor recebido deve ser igual ou superior ao valor total (" + totalVenda + ")");
        }

        novaVenda.setValorTotal(totalVenda);
        BigDecimal troco = request.getValorRecebido().subtract(totalVenda);
        novaVenda.setTroco(troco);

        Venda vendaSalva = vendaRepository.save(novaVenda);

        return toResponseDTO(vendaSalva);
    }
}