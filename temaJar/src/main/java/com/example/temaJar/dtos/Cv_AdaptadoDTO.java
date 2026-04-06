package com.example.temaJar.dtos;

import java.time.LocalDate;

public class Cv_AdaptadoDTO {

    private Long id;
    private Long idCvBase; // Referencia al CV original
    private Long idPuesto; // Referencia al puesto (ej: Senior Java Dev)
    private String contenido; // El texto final generado por la IA
    private LocalDate fechaCreate;

    public Cv_AdaptadoDTO() {
    }

    public Cv_AdaptadoDTO(Long id, Long idCvBase, Long idPuesto, String contenido, LocalDate fechaCreate) {
        this.id = id;
        this.idCvBase = idCvBase;
        this.idPuesto = idPuesto;
        this.contenido = contenido;
        this.fechaCreate = fechaCreate;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdCvBase() { return idCvBase; }
    public void setIdCvBase(Long idCvBase) { this.idCvBase = idCvBase; }

    public Long getIdPuesto() { return idPuesto; }
    public void setIdPuesto(Long idPuesto) { this.idPuesto = idPuesto; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDate getFechaCreate() { return fechaCreate; }
    public void setFechaCreate(LocalDate fechaCreate) { this.fechaCreate = fechaCreate; }
}