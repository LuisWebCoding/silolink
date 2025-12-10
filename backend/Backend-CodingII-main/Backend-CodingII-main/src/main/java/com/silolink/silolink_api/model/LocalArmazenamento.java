package com.silolink.silolink_api.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "local_armazenamento")
public class LocalArmazenamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_local")
    private Integer idLocal;

    @Column(name = "nome_local")
    private String nomeLocal;

    @Column(name = "tipo_local")
    private String tipoLocal;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @OneToMany(mappedBy = "localArmazenamento", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Lote> lotes;

    @OneToMany(mappedBy = "localArmazenamento", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Sensor> sensores;

    // Getters e Setters
    public Integer getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public String getTipoLocal() {
        return tipoLocal;
    }

    public void setTipoLocal(String tipoLocal) {
        this.tipoLocal = tipoLocal;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Lote> getLotes() {
        return lotes;
    }

    public void setLotes(List<Lote> lotes) {
        this.lotes = lotes;
    }

    public List<Sensor> getSensores() {
        return sensores;
    }

    public void setSensores(List<Sensor> sensores) {
        this.sensores = sensores;
    }
}