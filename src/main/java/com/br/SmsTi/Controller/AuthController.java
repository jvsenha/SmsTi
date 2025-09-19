package com.br.SmsTi.Controller;

import com.br.SmsTi.DTO.LoginRequest;
import com.br.SmsTi.DTO.LoginResponse;
import com.br.SmsTi.Service.UserService;
import com.br.SmsTi.Util.TokenBlacklist;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private TokenBlacklist tokenBlacklist;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest dto, HttpServletResponse response) {
        try {
            // Autentica e gera token
            LoginResponse loginResponse = userService.autenticar(dto);

            // Cria e configura o cookie HttpOnly para o token JWT
            ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", loginResponse.getToken())
                    .httpOnly(true)
                    .secure(true) // Use 'true' em produção com HTTPS
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .sameSite("Strict")
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());

            // Retorna o corpo da resposta com o status 200 OK
            return ResponseEntity.ok(loginResponse);

        } catch (ResponseStatusException e) {
            // Se o UserService lançar uma exceção com status HTTP, o Controller
            // apenas a repassa.
            throw e;
        } catch (Exception e) {
            // Captura qualquer outra exceção inesperada e retorna um erro 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String authHeader, HttpServletResponse response) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklist.add(token);
        }

        // Invalida o cookie JWT forçando a expiração
        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        // Retorna um status 204 No Content, que é o ideal para logout bem-sucedido
        return ResponseEntity.noContent().build();
    }
}