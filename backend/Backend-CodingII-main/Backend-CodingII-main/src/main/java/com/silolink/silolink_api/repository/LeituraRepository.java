package com.silolink.silolink_api.repository;

import com.silolink.silolink_api.model.Leitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeituraRepository extends JpaRepository<Leitura, Integer> {

}