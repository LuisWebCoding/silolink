package com.silolink.silolink_api.repository;

import com.silolink.silolink_api.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Integer> {
}