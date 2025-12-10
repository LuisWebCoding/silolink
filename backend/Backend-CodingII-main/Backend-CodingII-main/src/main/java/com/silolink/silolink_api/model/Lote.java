package com.silolink.silolink_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lote")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Integer idLote;

    @Column(name = "tipo_semente")
    private String tipoSemente;

    @Column(name = "quantidade")
    private BigDecimal quantidade;

    @Column(name = "data_entrada")
    private LocalDateTime dataEntrada;

    @Column(name = "data_saida")
    private LocalDateTime dataSaida;

    @ManyToOne
    @JoinColumn(name = "id_empresa_dona")
    private Empresa empresaDona;

    @ManyToOne
    @JoinColumn(name = "id_comprador")
    private Comprador comprador;

    @ManyToOne
    @JoinColumn(name = "id_local")
    private LocalArmazenamento localArmazenamento;

    // Getters e Setters
    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public String getTipoSemente() {
        return tipoSemente;
    }

    public void setTipoSemente(String tipoSemente) {
        this.tipoSemente = tipoSemente;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Empresa getEmpresaDona() {
        return empresaDona;
    }

    public void setEmpresaDona(Empresa empresaDona) {
        this.empresaDona = empresaDona;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public LocalArmazenamento getLocalArmazenamento() {
        return localArmazenamento;
    }

    public void setLocalArmazenamento(LocalArmazenamento localArmazenamento) {
        this.localArmazenamento = localArmazenamento;
    }
}