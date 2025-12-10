package com.silolink.silolink_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoteDTO {
    private Integer idLote;
    private String tipoSemente;
    private BigDecimal quantidade;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private EmpresaDTO empresaDona;
    private CompradorDTO comprador;

    // Getters e Setters
    public Integer getIdLote() { return idLote; }
    public void setIdLote(Integer idLote) { this.idLote = idLote; }
    public String getTipoSemente() { return tipoSemente; }
    public void setTipoSemente(String tipoSemente) { this.tipoSemente = tipoSemente; }
    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
    public LocalDateTime getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDateTime dataEntrada) { this.dataEntrada = dataEntrada; }
    public LocalDateTime getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDateTime dataSaida) { this.dataSaida = dataSaida; }
    public EmpresaDTO getEmpresaDona() { return empresaDona; }
    public void setEmpresaDona(EmpresaDTO empresaDona) { this.empresaDona = empresaDona; }
    public CompradorDTO getComprador() { return comprador; }
    public void setComprador(CompradorDTO comprador) { this.comprador = comprador; }
}
