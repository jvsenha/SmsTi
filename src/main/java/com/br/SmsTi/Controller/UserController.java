package com.br.SmsTi.Controller;

import com.br.SmsTi.DTO.UserDTO;
import com.br.SmsTi.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/cadastrar")
    public ResponseEntity<UserDTO> cadastrarUsuario(@Valid @RequestBody UserDTO dto) {
        UserDTO response = userService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UserDTO>> listarTodosUsuarios() {
        List<UserDTO> response = userService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscarUsuarioPorId(@PathVariable Long id) {
        UserDTO response = userService.buscarUsuario(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<UserDTO> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
        UserDTO response = userService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        userService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}