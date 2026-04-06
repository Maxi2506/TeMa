package com.example.temaJar.dtos;

public class Cv_BaseDTO {

    private Long id;
    private Long idUsuario; // Referencia al Usuario (nombre_usuario)
    private String resumenProfesional;
    private int telefono;
    private String domicilio;
    private String linkedinUrl;
    private Long idLocalidad;
    private Long idProvincia;
    private Long idPais;

    public Cv_BaseDTO() {
    }

    public Cv_BaseDTO(Long id, Long idUsuario, String resumenProfesional, int telefono,
                     String domicilio, String linkedinUrl, Long idLocalidad,
                     Long idProvincia, Long idPais) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.resumenProfesional = resumenProfesional;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.linkedinUrl = linkedinUrl;
        this.idLocalidad = idLocalidad;
        this.idProvincia = idProvincia;
        this.idPais = idPais;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getResumenProfesional() { return resumenProfesional; }
    public void setResumenProfesional(String resumenProfesional) { this.resumenProfesional = resumenProfesional; }

    public int getTelefono() { return telefono; }
    public void setTelefono(int telefono) { this.telefono = telefono; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

    public Long getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Long idLocalidad) { this.idLocalidad = idLocalidad; }

    public Long getIdProvincia() { return idProvincia; }
    public void setIdProvincia(Long idProvincia) { this.idProvincia = idProvincia; }

    public Long getIdPais() { return idPais; }
    public void setIdPais(Long idPais) { this.idPais = idPais; }
}