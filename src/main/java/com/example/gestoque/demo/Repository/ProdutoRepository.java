package com.example.gestoque.demo.Repository;

import com.example.gestoque.demo.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Produto findByCodigoProduto(String codigoProduto);
}