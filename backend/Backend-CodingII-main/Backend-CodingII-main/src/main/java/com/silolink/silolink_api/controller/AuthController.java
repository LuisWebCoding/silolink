package com.silolink.silolink_api.controller;

import com.silolink.silolink_api.dto.LoginRequest;
import com.silolink.silolink_api.dto.LoginResponse;
import com.silolink.silolink_api.model.Usuario;
import com.silolink.silolink_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth" )
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Busca o usuário pelo email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getUsername());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401)
                    .body(new LoginResponse(false, "Usuário não encontrado"));
        }

        Usuario usuario = usuarioOpt.get();

        // Verifica a senha
        if (!usuario.getSenha().equals(request.getSenha())) {
            return ResponseEntity.status(401)
                    .body(new LoginResponse(false, "Senha incorreta"));
        }

        // Login bem-sucedido
        return ResponseEntity.ok(new LoginResponse(true, "Login realizado com sucesso"));
    }
}
