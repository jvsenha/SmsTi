package com.br.SmsTi.Controller;

import com.br.SmsTi.DTO.ComentarioRequest;
import com.br.SmsTi.DTO.ComentarioResponse;
import com.br.SmsTi.Service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/chamados/{chamadoId}/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<ComentarioResponse> criarComentario(
            @PathVariable Long chamadoId,
            @Valid @RequestBody ComentarioRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String usuarioEmail = authentication.getName();

            ComentarioResponse response = comentarioService.criarComentario(chamadoId, usuarioEmail, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar comentário", e);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ComentarioResponse>> getComentariosPorChamado(@PathVariable Long chamadoId) {
        try {
            List<ComentarioResponse> response = comentarioService.getComentariosPorChamado(chamadoId);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar comentários", e);
        }
    }
}