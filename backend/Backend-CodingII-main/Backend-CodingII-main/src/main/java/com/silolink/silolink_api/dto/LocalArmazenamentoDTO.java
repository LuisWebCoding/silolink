package com.silolink.silolink_api.dto;

import java.util.List;
import java.util.ArrayList;

public class LocalArmazenamentoDTO {
    private Integer idLocal;
    private String nomeLocal;
    private String tipoLocal;
    private EmpresaDTO empresa;
    private List<LoteDTO> lotes;
    private List<SensorDTO> sensores;

    // Construtores
    public LocalArmazenamentoDTO() {
        this.lotes = new ArrayList<>();
        this.sensores = new ArrayList<>();
    }

    public LocalArmazenamentoDTO(Integer idLocal, String nomeLocal, String tipoLocal) {
        this.idLocal = idLocal;
        this.nomeLocal = nomeLocal;
        this.tipoLocal = tipoLocal;
        this.lotes = new ArrayList<>();
        this.sensores = new ArrayList<>();
    }

    // Getters e Setters
    public Integer getIdLocal() { return idLocal; }
    public void setIdLocal(Integer idLocal) { this.idLocal = idLocal; }
    public String getNomeLocal() { return nomeLocal; }
    public void setNomeLocal(String nomeLocal) { this.nomeLocal = nomeLocal; }
    public String getTipoLocal() { return tipoLocal; }
    public void setTipoLocal(String tipoLocal) { this.tipoLocal = tipoLocal; }
    public EmpresaDTO getEmpresa() { return empresa; }
    public void setEmpresa(EmpresaDTO empresa) { this.empresa = empresa; }

    public List<LoteDTO> getLotes() { return lotes; }
    public void setLotes(List<LoteDTO> lotes) { this.lotes = lotes; }

    public List<SensorDTO> getSensores() { return sensores; }
    public void setSensores(List<SensorDTO> sensores) { this.sensores = sensores; }
}