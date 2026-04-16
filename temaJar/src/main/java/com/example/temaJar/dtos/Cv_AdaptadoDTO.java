package com.example.temaJar.dtos;

import java.time.LocalDate;
import java.util.List;

public class Cv_AdaptadoDTO {

    private Long id;
    private Long idCvBase;
    private Long idPuesto;
    private String contenido;
    private LocalDate fechaCreate;
    private List<String> habilidadesNombres; // Nueva lista para el DTO

    public Cv_AdaptadoDTO() {}

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
    public List<String> getHabilidadesNombres() { return habilidadesNombres; }
    public void setHabilidadesNombres(List<String> habilidadesNombres) { this.habilidadesNombres = habilidadesNombres; }
}