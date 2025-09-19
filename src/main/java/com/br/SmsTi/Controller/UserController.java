package com.br.SmsTi.Controller;

import com.br.SmsTi.DTO.UserDTO;
import com.br.SmsTi.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/cadastrar")
    @PreAuthorize("hasAnyRole('Nivel3')")
    public ResponseEntity<UserDTO> cadastrarUsuario(@Valid @RequestBody UserDTO dto) {
        try {
            UserDTO response = userService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao cadastrar usuário", e);
        }
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('Nivel3', 'Nivel2')")
    public ResponseEntity<List<UserDTO>> listarTodosUsuarios() {
        try {
            List<UserDTO> response = userService.listarTodos();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar usuários", e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Nivel3', 'Nivel2', 'Nivel1')")
    public ResponseEntity<UserDTO> buscarUsuarioPorId(@PathVariable Long id) {
        try {
            UserDTO response = userService.buscarUsuario(id);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar usuário por ID", e);
        }
    }

    @PutMapping("/alterar/{id}")
    @PreAuthorize("hasAnyRole('Nivel3')")
    public ResponseEntity<UserDTO> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
        try {
            UserDTO response = userService.atualizar(id, dto);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar usuário", e);
        }
    }

    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("hasAnyRole('Nivel3')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        try {
            userService.deletarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar usuário", e);
        }
    }
}