package com.silolink.silolink_api.repository;

import com.silolink.silolink_api.model.LocalArmazenamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalArmazenamentoRepository extends JpaRepository<LocalArmazenamento, Integer> {
}