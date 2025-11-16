package com.example.gestoque.demo.Repository;

import com.example.gestoque.demo.Model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long>, JpaSpecificationExecutor<Venda> {

    List<Venda> findByDataHoraBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);
    List<Venda> findByValorTotalBetween(BigDecimal valorMin, BigDecimal valorMax);
    List<Venda> findByUsuarioResponsavelId(Long usuarioId);
}