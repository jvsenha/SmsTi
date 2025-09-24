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

        // Sem try-catch: a lógica de negócio fica mais clara
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usuarioEmail = authentication.getName();

        ComentarioResponse response = comentarioService.criarComentario(chamadoId, usuarioEmail, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ComentarioResponse>> getComentariosPorChamado(@PathVariable Long chamadoId) {
        // Sem try-catch aqui também
        List<ComentarioResponse> response = comentarioService.getComentariosPorChamado(chamadoId);
        return ResponseEntity.ok(response);
    }
}