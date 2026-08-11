package com.example.temaJar.dtos;

import java.util.List;

public class Cv_BaseDTO {

    private Long id;
    private String correoUsuario;
    private String resumenProfesional;
    private Long telefono;
    private String domicilio;
    private String linkedinUrl;
    private String nombreLocalidad;
    private String nombreProvincia;
    private String nombrePais;
    private List<String> habilidadesNombres;

    public Cv_BaseDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }
    public String getResumenProfesional() { return resumenProfesional; }
    public void setResumenProfesional(String resumenProfesional) { this.resumenProfesional = resumenProfesional; }
    public Long getTelefono() { return telefono; }
    public void setTelefono(Long telefono) { this.telefono = telefono; }
    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }
    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    public String getNombreLocalidad() { return nombreLocalidad; }
    public void setNombreLocalidad(String nombreLocalidad) { this.nombreLocalidad = nombreLocalidad; }
    public String getNombreProvincia() { return nombreProvincia; }
    public void setNombreProvincia(String nombreProvincia) { this.nombreProvincia = nombreProvincia; }
    public String getNombrePais() { return nombrePais; }
    public void setNombrePais(String nombrePais) { this.nombrePais = nombrePais; }
    public List<String> getHabilidadesNombres() { return habilidadesNombres; }
    public void setHabilidadesNombres(List<String> habilidadesNombres) { this.habilidadesNombres = habilidadesNombres; }
}