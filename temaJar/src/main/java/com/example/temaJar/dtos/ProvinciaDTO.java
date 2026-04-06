package com.example.temaJar.dtos;

public class ProvinciaDTO {
    private Long id;
    private String nombre;
    private String nombrePais; // Cambiado de Long a String para recibir el nombre

    public ProvinciaDTO() {
    }

    public ProvinciaDTO(Long id, String nombre, String nombrePais) {
        this.id = id;
        this.nombre = nombre;
        this.nombrePais = nombrePais;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombrePais() { return nombrePais; }
    public void setNombrePais(String nombrePais) { this.nombrePais = nombrePais; }
}