package com.example.temaJar.dtos;

public class LocalidadDTO {
    private Long id;
    private String nombre;
    private String nombreProvincia; // Cambiado de Long a String para recibir el nombre

    public LocalidadDTO() {
    }

    public LocalidadDTO(Long id, String nombre, String nombreProvincia) {
        this.id = id;
        this.nombre = nombre;
        this.nombreProvincia = nombreProvincia;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombreProvincia() { return nombreProvincia; }
    public void setNombreProvincia(String nombreProvincia) { this.nombreProvincia = nombreProvincia; }
}