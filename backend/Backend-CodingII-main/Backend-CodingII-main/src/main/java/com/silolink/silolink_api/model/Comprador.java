package com.silolink.silolink_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comprador")
public class Comprador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprador")
    private Integer idComprador;

    @Column(name = "nome")
    private String nome;

    @Column(name = "documento")
    private String documento;

    @Column(name = "contato")
    private String contato;

    // Getters e Setters
    public Integer getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(Integer idComprador) {
        this.idComprador = idComprador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}