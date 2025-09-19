package com.br.SmsTi.Controller;

import com.br.SmsTi.DTO.UnidadeRequest;
import com.br.SmsTi.DTO.UnidadeResponse;
import com.br.SmsTi.Service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
public class UnidadeController {

    @Autowired
    private UnidadeService unidadeService;

    @PostMapping("/cadastrar")
    @PreAuthorize("hasAnyRole('Nivel3', 'Nivel2')")
    public ResponseEntity<UnidadeResponse> criarUnidade(@Valid @RequestBody UnidadeRequest request) {
        try {
            UnidadeResponse response = unidadeService.cadastrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResponseStatusException e) {
            throw e; // Lança a exceção para ser tratada pelo Spring ou um @ControllerAdvice
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar unidade", e);
        }
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('Nivel3', 'Nivel2')")
    public ResponseEntity<List<UnidadeResponse>> listarTodasUnidades() {
        try {
            List<UnidadeResponse> response = unidadeService.listarTodos();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar unidades", e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Nivel3', 'Nivel2')")
    public ResponseEntity<UnidadeResponse> buscarUnidadePorId(@PathVariable Long id) {
        try {
            UnidadeResponse response = unidadeService.buscarUnidade(id);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar unidade por ID", e);
        }
    }

    @PutMapping("/alterar/{id}")
    @PreAuthorize("hasAnyRole('Nivel3', 'Nivel2')")
    public ResponseEntity<UnidadeResponse> atualizarUnidade(@PathVariable Long id, @Valid @RequestBody UnidadeRequest request) {
        try {
            UnidadeResponse response = unidadeService.atualizar(id, request);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar unidade", e);
        }
    }

    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("hasRole('Nivel3')")
    public ResponseEntity<Void> deletarUnidade(@PathVariable Long id) {
        try {
            unidadeService.deletarUnidade(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar unidade", e);
        }
    }

    @PatchMapping("/alterar/status/{id}")
    @PreAuthorize("hasAnyRole('Nivel3', 'Nivel2')")
    public ResponseEntity<UnidadeResponse> alterarStatusUnidade(@PathVariable Long id) {
        try {
            UnidadeResponse response = unidadeService.alternarStatus(id);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao alterar status da unidade", e);
        }
    }
}