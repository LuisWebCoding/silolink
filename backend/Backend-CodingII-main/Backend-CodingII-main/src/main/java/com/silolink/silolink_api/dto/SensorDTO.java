package com.silolink.silolink_api.dto;

public class SensorDTO {
    private Integer idSensor;
    private String tipoSensor;
    private String status;
    private LocalArmazenamentoDTO localArmazenamento;

    // Getters e Setters
    public Integer getIdSensor() { return idSensor; }
    public void setIdSensor(Integer idSensor) { this.idSensor = idSensor; }
    public String getTipoSensor() { return tipoSensor; }
    public void setTipoSensor(String tipoSensor) { this.tipoSensor = tipoSensor; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalArmazenamentoDTO getLocalArmazenamento() { return localArmazenamento; }
    public void setLocalArmazenamento(LocalArmazenamentoDTO localArmazenamento) { this.localArmazenamento = localArmazenamento; }
}