package com.example.gestoque.demo.Service;

import com.example.gestoque.demo.Model.Venda;
import com.example.gestoque.demo.Model.ItemVenda;
import com.example.gestoque.demo.Model.Produto;
import com.example.gestoque.demo.Repository.VendaRepository;
import com.example.gestoque.demo.Repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ItemVendaRepository itemVendaRepository;
    private final ProdutoService produtoService;

    @Autowired
    public VendaService(VendaRepository vendaRepository, ItemVendaRepository itemVendaRepository, ProdutoService produtoService) {
        this.vendaRepository = vendaRepository;
        this.itemVendaRepository = itemVendaRepository;
        this.produtoService = produtoService;
    }

    public List<Venda> listarTodos() {
        return vendaRepository.findAll();
    }

    public List<Venda> buscarPorUsuario(Long usuarioId) {
        return vendaRepository.findByUsuarioResponsavelId(usuarioId);
    }

    @Transactional
    public Venda registrarVenda(Venda venda) {

        BigDecimal total = BigDecimal.ZERO;

        if (venda.getItens() == null || venda.getItens().isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter pelo menos um item.");
        }

        for (ItemVenda item : venda.getItens()) {
            Optional<Produto> produtoOpt = produtoService.buscarPorId(item.getProduto().getId());

            if (!produtoOpt.isPresent()) {
                throw new IllegalArgumentException("Produto com ID " + item.getProduto().getId() + " não encontrado.");
            }

            Produto produto = produtoOpt.get();
            int quantidadeVendida = item.getQuantidade();

            if (quantidadeVendida <= 0 || quantidadeVendida > produto.getQuantidadeEstoque()) {
                throw new IllegalArgumentException("Quantidade inválida ou insuficiente em estoque para o produto: " + produto.getNome());
            }

            item.setProduto(produto);
            item.setPrecoUnitarioVenda(produto.getPrecoUnitario());
            BigDecimal subtotal = produto.getPrecoUnitario().multiply(new BigDecimal(quantidadeVendida));
            item.setSubtotal(subtotal);

            total = total.add(subtotal);

            produtoService.atualizarEstoque(produto, -quantidadeVendida);

            item.setVenda(venda);
        }

        if (venda.getValorRecebido() == null || venda.getValorRecebido().compareTo(total) < 0) {
            throw new IllegalArgumentException("O valor recebido deve ser igual ou superior ao valor total (" + total + ")");
        }

        venda.setValorTotal(total);

        BigDecimal troco = venda.getValorRecebido().subtract(total);
        venda.setTroco(troco);

        return vendaRepository.save(venda);
    }
}