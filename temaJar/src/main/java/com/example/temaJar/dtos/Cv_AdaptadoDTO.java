package com.example.temaJar.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cv_AdaptadoDTO {

    private Long id;
    private Long idCvBase;
    private Long idPuesto;
    private String contenido;

    // Le decimos a Spring exactamente cómo leer la fecha de Postman
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCreate;

    private List<String> habilidadesNombres;

    public Cv_AdaptadoDTO() {}

    public Cv_AdaptadoDTO(Long id, Long idCvBase, Long idPuesto, String contenido, LocalDate fechaCreate, List<String> habilidadesNombres) {
        this.id = id;
        this.idCvBase = idCvBase;
        this.idPuesto = idPuesto;
        this.contenido = contenido;
        this.fechaCreate = fechaCreate;
        this.habilidadesNombres = habilidadesNombres;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdCvBase() { return idCvBase; }
    public void setIdCvBase(Long idCvBase) { this.idCvBase = idCvBase; }

    public Long getIdPuesto() { return idPuesto; }
    public void setIdPuesto(Long idPuesto) { this.idPuesto = idPuesto; }

    // ¡Corregido! Antes decía getContentido
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDate getFechaCreate() { return fechaCreate; }
    public void setFechaCreate(LocalDate fechaCreate) { this.fechaCreate = fechaCreate; }

    public List<String> getHabilidadesNombres() { return habilidadesNombres; }
    public void setHabilidadesNombres(List<String> habilidadesNombres) { this.habilidadesNombres = habilidadesNombres; }
}