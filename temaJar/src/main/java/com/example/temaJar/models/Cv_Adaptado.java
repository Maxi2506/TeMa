package com.example.temaJar.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="Cv_Adaptado")
public class Cv_Adaptado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cv_base")
    private Cv_Base cv_base;

    @ManyToOne
    @JoinColumn(name = "id_puesto")
    private Puesto puesto;

    @Column(name="contenido")
    private String contenido;

    @Column(name="fecha_create")
    private LocalDate fecha_create;

    public Cv_Adaptado() {
    }

    public Cv_Adaptado(Long id, Cv_Base cv_base, Puesto puesto, String contenido, LocalDate fecha_create) {
        this.id = id;
        this.cv_base = cv_base;
        this.puesto = puesto;
        this.contenido = contenido;
        this.fecha_create = fecha_create;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cv_Base getCv_base() {
        return cv_base;
    }

    public void setCv_base(Cv_Base cv_base) {
        this.cv_base = cv_base;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFecha_create() {
        return fecha_create;
    }

    public void setFecha_create(LocalDate fecha_create) {
        this.fecha_create = fecha_create;
    }

    @Override
    public String toString() {
        return "Cv_Adaptado{" +
                "id=" + id +
                ", cv_base=" + cv_base +
                ", puesto=" + puesto +
                ", contenido='" + contenido + '\'' +
                ", fecha_create=" + fecha_create +
                '}';
    }
}
