package com.silolink.silolink_api.repository;

import com.silolink.silolink_api.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
}