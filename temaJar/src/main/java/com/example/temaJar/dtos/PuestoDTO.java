package com.example.temaJar.dtos;

public class PuestoDTO {

    private String nombre;
    private String categoria; // Recibe "DESARROLLO DE SOFTWARE"
    private String nivel;     // Recibe "TRAINEE"

    public PuestoDTO() {
    }

    public PuestoDTO(String nombre, String categoria, String nivel) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.nivel = nivel;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

}