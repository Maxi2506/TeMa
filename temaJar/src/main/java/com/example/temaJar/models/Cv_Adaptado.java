package com.example.temaJar.models;

import com.example.temaJar.enumeracion.Habilidad;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Cv_Adaptado")
public class Cv_Adaptado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cv_base")
    private Cv_Base cvBase;

    @ManyToOne
    @JoinColumn(name = "id_puesto")
    private Puesto puesto;

    @Column(name="contenido", columnDefinition = "TEXT")
    private String contenido;

    @Column(name="fecha_create")
    private LocalDate fechaCreate;

    // --- Habilidades seleccionadas para esta adaptación ---
    @ElementCollection(targetClass = Habilidad.class)
    @CollectionTable(name = "cv_adaptado_habilidades", joinColumns = @JoinColumn(name = "id_cv_adaptado"))
    @Enumerated(EnumType.STRING)
    @Column(name = "habilidad")
    private List<Habilidad> habilidades = new ArrayList<>();

    public Cv_Adaptado() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cv_Base getCvBase() { return cvBase; }
    public void setCvBase(Cv_Base cvBase) { this.cvBase = cvBase; }
    public Puesto getPuesto() { return puesto; }
    public void setPuesto(Puesto puesto) { this.puesto = puesto; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public LocalDate getFechaCreate() { return fechaCreate; }
    public void setFechaCreate(LocalDate fechaCreate) { this.fechaCreate = fechaCreate; }
    public List<Habilidad> getHabilidades() { return habilidades; }
    public void setHabilidades(List<Habilidad> habilidades) { this.habilidades = habilidades; }
}