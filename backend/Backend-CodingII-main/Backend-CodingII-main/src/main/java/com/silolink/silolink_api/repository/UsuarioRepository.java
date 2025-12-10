package com.silolink.silolink_api.repository;

import com.silolink.silolink_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String username);
}