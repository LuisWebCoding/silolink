package com.silolink.silolink_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Integer idAlerta;

    @Column(name = "tipo_alerta")
    private String tipoAlerta;

    @Column(name = "valor_lido")
    private BigDecimal valorLido;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "nivel_risco")
    private String nivelRisco;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "id_sensor")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "id_lote")
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "id_local")
    private LocalArmazenamento localArmazenamento;

    @ManyToOne
    @JoinColumn(name = "id_usuario_visualizou")
    private Usuario usuarioVisualizou;

    // Getters e Setters
    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public String getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public BigDecimal getValorLido() {
        return valorLido;
    }

    public void setValorLido(BigDecimal valorLido) {
        this.valorLido = valorLido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(String nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public LocalArmazenamento getLocalArmazenamento() {
        return localArmazenamento;
    }

    public void setLocalArmazenamento(LocalArmazenamento localArmazenamento) {
        this.localArmazenamento = localArmazenamento;
    }

    public Usuario getUsuarioVisualizou() {
        return usuarioVisualizou;
    }

    public void setUsuarioVisualizou(Usuario usuarioVisualizou) {
        this.usuarioVisualizou = usuarioVisualizou;
    }
}