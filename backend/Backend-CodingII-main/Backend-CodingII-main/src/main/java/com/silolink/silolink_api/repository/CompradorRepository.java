package com.silolink.silolink_api.repository;

import com.silolink.silolink_api.model.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, Integer> {
}