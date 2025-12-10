package com.silolink.silolink_api.repository;

import com.silolink.silolink_api.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Integer> {
}