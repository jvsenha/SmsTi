package com.br.SmsTi.Controller;

import com.br.SmsTi.DTO.UnidadeRequest;
import com.br.SmsTi.DTO.UnidadeResponse;
import com.br.SmsTi.Service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
public class UnidadeController {

    @Autowired
    private UnidadeService unidadeService;

    @PostMapping("/cadastrar")
    public ResponseEntity<UnidadeResponse> criarUnidade(@Valid @RequestBody UnidadeRequest request) {
        UnidadeResponse response = unidadeService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UnidadeResponse>> listarTodasUnidades() {
        List<UnidadeResponse> response = unidadeService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeResponse> buscarUnidadePorId(@PathVariable Long id) {
        UnidadeResponse response = unidadeService.buscarUnidade(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<UnidadeResponse> atualizarUnidade(@PathVariable Long id, @Valid @RequestBody UnidadeRequest request) {
        UnidadeResponse response = unidadeService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUnidade(@PathVariable Long id) {
        unidadeService.deletarUnidade(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/alterar/status/{id}")
    public ResponseEntity<UnidadeResponse> alterarStatusUnidade(@PathVariable Long id) {
        UnidadeResponse response = unidadeService.alternarStatus(id);
        return ResponseEntity.ok(response);
    }
}