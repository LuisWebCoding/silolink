package com.silolink.silolink_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class AlertaDTO {
    private Integer idAlerta;
    private String tipoAlerta;
    private BigDecimal valorLido;
    private String descricao;
    private String nivelRisco;
    private LocalDateTime dataHora;
    private SensorDTO sensor;
    private LoteDTO lote;
    private LocalArmazenamentoDTO localArmazenamento;
    private UsuarioDTO usuarioVisualizou;

    // Getters e Setters
    public Integer getIdAlerta() { return idAlerta; }
    public void setIdAlerta(Integer idAlerta) { this.idAlerta = idAlerta; }
    public String getTipoAlerta() { return tipoAlerta; }
    public void setTipoAlerta(String tipoAlerta) { this.tipoAlerta = tipoAlerta; }
    public BigDecimal getValorLido() { return valorLido; }
    public void setValorLido(BigDecimal valorLido) { this.valorLido = valorLido; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getNivelRisco() { return nivelRisco; }
    public void setNivelRisco(String nivelRisco) { this.nivelRisco = nivelRisco; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public SensorDTO getSensor() { return sensor; }
    public void setSensor(SensorDTO sensor) { this.sensor = sensor; }
    public LoteDTO getLote() { return lote; }
    public void setLote(LoteDTO lote) { this.lote = lote; }
    public LocalArmazenamentoDTO getLocalArmazenamento() { return localArmazenamento; }
    public void setLocalArmazenamento(LocalArmazenamentoDTO localArmazenamento) { this.localArmazenamento = localArmazenamento; }
    public UsuarioDTO getUsuarioVisualizou() { return usuarioVisualizou; }
    public void setUsuarioVisualizou(UsuarioDTO usuarioVisualizou) { this.usuarioVisualizou = usuarioVisualizou; }
}