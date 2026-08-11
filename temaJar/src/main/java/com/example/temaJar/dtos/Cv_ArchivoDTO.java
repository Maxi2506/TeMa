package com.example.temaJar.dtos;

import java.time.LocalDate;

public class Cv_ArchivoDTO {

    private Long id;
    private Long idUsuario;
    private String nombreArchivo;
    private LocalDate fechaCarga;
    private String textoExtraido;

    // Constructor vacío
    public Cv_ArchivoDTO() {
    }

    // Constructor completo
    public Cv_ArchivoDTO(Long id, Long idUsuario, String nombreArchivo, LocalDate fechaCarga, String textoExtraido) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.nombreArchivo = nombreArchivo;
        this.fechaCarga = fechaCarga;
        this.textoExtraido = textoExtraido;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }

    public LocalDate getFechaCarga() { return fechaCarga; }
    public void setFechaCarga(LocalDate fechaCarga) { this.fechaCarga = fechaCarga; }

    public String getTextoExtraido() { return textoExtraido; }
    public void setTextoExtraido(String textoExtraido) { this.textoExtraido = textoExtraido; }
}