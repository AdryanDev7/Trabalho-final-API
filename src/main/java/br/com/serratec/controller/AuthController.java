package br.com.serratec.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.dto.LoginRequest;
import br.com.serratec.dto.LoginResponse;
import br.com.serratec.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    // Injeção de dependências via construtor
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    /**
     * Endpoint de Login
     * POST /api/auth/login
     * 
     * Recebe: { "email": "usuario@email.com", "senha": "123456" }
     * Retorna: { "token": "eyJhbGciOiJIUzI1...", "type": "Bearer" }
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // 1. Tenta autenticar o usuário com email e senha
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), 
                request.getSenha()
            )
        );

        // 2. Se a autenticação for bem-sucedida, gera o token JWT
        String token = tokenProvider.generateToken(authentication);
        
        // 3. Retorna o token para o cliente
        return ResponseEntity.ok(new LoginResponse(token, "Bearer"));
    }
}
