package com.silolink.silolink_api.dto;

public class UsuarioDTO {
    private Integer idUsuario;
    private String nome;
    private String cargo;
    private String email;
    private String senha;
    private String nivelAcesso;
    private EmpresaDTO empresa; // Relacionamento aninhado

    // Getters e Setters
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(String nivelAcesso) { this.nivelAcesso = nivelAcesso; }
    public EmpresaDTO getEmpresa() { return empresa; }
    public void setEmpresa(EmpresaDTO empresa) { this.empresa = empresa; }
}