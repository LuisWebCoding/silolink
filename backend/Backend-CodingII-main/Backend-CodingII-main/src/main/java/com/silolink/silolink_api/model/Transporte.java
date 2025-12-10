package com.silolink.silolink_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transporte")
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transporte")
    private Integer idTransporte;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column(name = "origem")
    private String origem;

    @Column(name = "destino")
    private String destino;

    @ManyToOne
    @JoinColumn(name = "id_lote")
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "id_local_veiculo")
    private LocalArmazenamento localVeiculo;

    // Getters e Setters
    public Integer getIdTransporte() {
        return idTransporte;
    }

    public void setIdTransporte(Integer idTransporte) {
        this.idTransporte = idTransporte;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public LocalArmazenamento getLocalVeiculo() {
        return localVeiculo;
    }

    public void setLocalVeiculo(LocalArmazenamento localVeiculo) {
        this.localVeiculo = localVeiculo;
    }
}