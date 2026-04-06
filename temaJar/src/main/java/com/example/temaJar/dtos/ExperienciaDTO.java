package com.example.temaJar.dtos;

import java.time.LocalDate;

public class ExperienciaDTO {
    private Long id;
    private String empresa;
    private String nombrePuesto; // Simplificamos el objeto Puesto a solo su nombre
    private Long idCvBase;       // Solo el ID, no toda la entidad Cv_Base
    private LocalDate fechaIni;
    private LocalDate fechaFin;

    public ExperienciaDTO() {}

    public ExperienciaDTO(Long id, String empresa, String nombrePuesto, Long idCvBase, LocalDate fechaIni, LocalDate fechaFin) {
        this.id = id;
        this.empresa = empresa;
        this.nombrePuesto = nombrePuesto;
        this.idCvBase = idCvBase;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public Long getIdCvBase() {
        return idCvBase;
    }

    public void setIdCvBase(Long idCvBase) {
        this.idCvBase = idCvBase;
    }

    public LocalDate getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(LocalDate fechaIni) {
        this.fechaIni = fechaIni;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}