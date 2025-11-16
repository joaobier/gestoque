package com.example.gestoque.demo.Controller;

import com.example.gestoque.demo.DTOs.Requests.MovimentacaoRequest;
import com.example.gestoque.demo.DTOs.Response.MovimentacaoResponse;
import com.example.gestoque.demo.Service.MovimentacaoEstoqueService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class MovimentacaoEstoqueController {

    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    @Autowired
    public MovimentacaoEstoqueController(MovimentacaoEstoqueService movimentacaoEstoqueService) {
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
    }

    @PostMapping("/movimentar")
    public ResponseEntity<?> registrarMovimentacao(@RequestBody MovimentacaoRequest request) {
        try {
            MovimentacaoResponse response = movimentacaoEstoqueService.registrarMovimentacao(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/historico")
    public ResponseEntity<List<MovimentacaoResponse>> listarHistorico() {
        List<MovimentacaoResponse> historico = movimentacaoEstoqueService.listarHistorico();
        return ResponseEntity.ok(historico);
    }
}