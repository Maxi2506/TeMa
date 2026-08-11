package com.example.temaJar.dtos;

public class Cv_HabilidadesDTO {
    private Long id;
    private Long idCvBase;
    private Long idHabilidad;
    private String nombreHabilidad; // Para que el front no tenga que consultar el catálogo

    public Cv_HabilidadesDTO() {}

    public Cv_HabilidadesDTO(Long id, Long idCvBase, Long idHabilidad, String nombreHabilidad) {
        this.id = id;
        this.idCvBase = idCvBase;
        this.idHabilidad = idHabilidad;
        this.nombreHabilidad = nombreHabilidad;
    }

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdCvBase() { return idCvBase; }
    public void setIdCvBase(Long idCvBase) { this.idCvBase = idCvBase; }
    public Long getIdHabilidad() { return idHabilidad; }
    public void setIdHabilidad(Long idHabilidad) { this.idHabilidad = idHabilidad; }
    public String getNombreHabilidad() { return nombreHabilidad; }
    public void setNombreHabilidad(String nombreHabilidad) { this.nombreHabilidad = nombreHabilidad; }
}