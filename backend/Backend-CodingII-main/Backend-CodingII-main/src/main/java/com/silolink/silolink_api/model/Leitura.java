package com.silolink.silolink_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "leitura")
public class Leitura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_leitura")
    private Integer idLeitura;

    @Column(name = "valor_temperatura")
    private BigDecimal valorTemperatura;

    @Column(name = "valor_umidade")
    private BigDecimal valorUmidade;

    @Column(name = "valor_luminosidade")
    private BigDecimal valorLuminosidade;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "id_sensor")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "id_local")
    private LocalArmazenamento localArmazenamento;

    @ManyToOne
    @JoinColumn(name = "id_lote")
    private Lote lote;

    // Getters e Setters
    public Integer getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(Integer idLeitura) {
        this.idLeitura = idLeitura;
    }

    public BigDecimal getValorTemperatura() {
        return valorTemperatura;
    }

    public void setValorTemperatura(BigDecimal valorTemperatura) {
        this.valorTemperatura = valorTemperatura;
    }

    public BigDecimal getValorUmidade() {
        return valorUmidade;
    }

    public void setValorUmidade(BigDecimal valorUmidade) {
        this.valorUmidade = valorUmidade;
    }

    public BigDecimal getValorLuminosidade() {
        return valorLuminosidade;
    }

    public void setValorLuminosidade(BigDecimal valorLuminosidade) {
        this.valorLuminosidade = valorLuminosidade;
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

    public LocalArmazenamento getLocalArmazenamento() {
        return localArmazenamento;
    }

    public void setLocalArmazenamento(LocalArmazenamento localArmazenamento) {
        this.localArmazenamento = localArmazenamento;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }
}