package com.silolink.silolink_api.service;

import com.silolink.silolink_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository repo;

    /**
     * Verifica se há um usuário com esse nome e senha.
     * @return true se credenciais válidas, false caso contrário
     */
    public boolean autenticar(String username, String senha) {
        return repo.findByEmail(username)
                .map(u -> u.getSenha().equals(senha))
                .orElse(false);
    }
}