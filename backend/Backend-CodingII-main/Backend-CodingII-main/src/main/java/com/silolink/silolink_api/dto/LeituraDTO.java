package com.silolink.silolink_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LeituraDTO {
    private Integer idLeitura;
    private BigDecimal valorTemperatura;
    private BigDecimal valorUmidade;
    private BigDecimal valorLuminosidade;
    private LocalDateTime dataHora;
    private SensorDTO sensor;
    private LocalArmazenamentoDTO localArmazenamento;
    private LoteDTO lote;

    // Getters e Setters
    public Integer getIdLeitura() { return idLeitura; }
    public void setIdLeitura(Integer idLeitura) { this.idLeitura = idLeitura; }
    public BigDecimal getValorTemperatura() { return valorTemperatura; }
    public void setValorTemperatura(BigDecimal valorTemperatura) { this.valorTemperatura = valorTemperatura; }
    public BigDecimal getValorUmidade() { return valorUmidade; }
    public void setValorUmidade(BigDecimal valorUmidade) { this.valorUmidade = valorUmidade; }
    public BigDecimal getValorLuminosidade() { return valorLuminosidade; }
    public void setValorLuminosidade(BigDecimal valorLuminosidade) { this.valorLuminosidade = valorLuminosidade; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public SensorDTO getSensor() { return sensor; }
    public void setSensor(SensorDTO sensor) { this.sensor = sensor; }
    public LocalArmazenamentoDTO getLocalArmazenamento() { return localArmazenamento; }
    public void setLocalArmazenamento(LocalArmazenamentoDTO localArmazenamento) { this.localArmazenamento = localArmazenamento; }
    public LoteDTO getLote() { return lote; }
    public void setLote(LoteDTO lote) { this.lote = lote; }
}