package com.example.gestoque.demo.Controller;

import com.example.gestoque.demo.DTOs.Response.RelatorioVendasResponse;
import com.example.gestoque.demo.Service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final VendaService vendaService;

    @Autowired
    public RelatorioController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @GetMapping("/vendas")
    public ResponseEntity<RelatorioVendasResponse> getRelatorioVendas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) BigDecimal valorMin,
            @RequestParam(required = false) BigDecimal valorMax
    ) {
        RelatorioVendasResponse relatorio = vendaService.gerarRelatorioVendas(dataInicio, dataFim, usuarioId, valorMin, valorMax);
        return ResponseEntity.ok(relatorio);
    }
}