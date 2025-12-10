package com.silolink.silolink_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sensor")
    private Integer idSensor;

    @Column(name = "tipo_sensor")
    private String tipoSensor;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_local")
    private LocalArmazenamento localArmazenamento;

    // Getters e Setters
    public Integer getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Integer idSensor) {
        this.idSensor = idSensor;
    }

    public String getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(String tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalArmazenamento getLocalArmazenamento() {
        return localArmazenamento;
    }

    public void setLocalArmazenamento(LocalArmazenamento localArmazenamento) {
        this.localArmazenamento = localArmazenamento;
    }
}