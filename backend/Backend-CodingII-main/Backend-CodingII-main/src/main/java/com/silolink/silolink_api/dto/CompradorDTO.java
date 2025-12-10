package com.silolink.silolink_api.dto;

public class CompradorDTO {
    private Integer idComprador;
    private String nome;
    private String documento;
    private String contato;

    // Getters e Setters
    public Integer getIdComprador() { return idComprador; }
    public void setIdComprador(Integer idComprador) { this.idComprador = idComprador; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getContato() { return contato; }
    public void setContato(String contato) { this.contato = contato; }
}