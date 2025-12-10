package com.silolink.silolink_api.dto;

import java.time.LocalDateTime;

public class TransporteDTO {
    private Integer idTransporte;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String origem;
    private String destino;
    private LoteDTO lote;
    private LocalArmazenamentoDTO localVeiculo;

    // Getters e Setters
    public Integer getIdTransporte() { return idTransporte; }
    public void setIdTransporte(Integer idTransporte) { this.idTransporte = idTransporte; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    public LoteDTO getLote() { return lote; }
    public void setLote(LoteDTO lote) { this.lote = lote; }
    public LocalArmazenamentoDTO getLocalVeiculo() { return localVeiculo; }
    public void setLocalVeiculo(LocalArmazenamentoDTO localVeiculo) { this.localVeiculo = localVeiculo; }
}