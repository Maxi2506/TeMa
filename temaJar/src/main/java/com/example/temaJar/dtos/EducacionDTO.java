package com.example.temaJar.dtos;

import com.example.temaJar.models.Cv_Base;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class EducacionDTO {

    private Long idCvBase;
    private String institucion;
    private String titulo;
    private int duracion;

    public EducacionDTO() {
    }

    public EducacionDTO(Long idCvBase, String institucion, String titulo, int duracion) {
        this.idCvBase = idCvBase;
        this.institucion = institucion;
        this.titulo = titulo;
        this.duracion = duracion;
    }

    public Long getIdCvBase() {
        return idCvBase;
    }

    public void setIdCvBase(Long idCvBase) {
        this.idCvBase = idCvBase;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
