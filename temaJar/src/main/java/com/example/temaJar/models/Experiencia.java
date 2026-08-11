package com.example.temaJar.models;

import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Entity
@Table(name="experiencia")
public class Experiencia {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cv_base")
    private Cv_Base id_cv_base;

    @Column(name="empresa")
    private String empresa;

    @ManyToOne
    @JoinColumn(name = "id_puesto")
    private Puesto puesto;

    @Column(name="fecha_ini")
    private LocalDate fecha_ini;

    @Column(name="fecha_fin")
    private LocalDate fecha_fin;

    public Experiencia() {
    }

    public Experiencia(Long id, Cv_Base id_cv, String empresa, Puesto puesto, LocalDate fecha_ini, LocalDate fecha_fin) {
        this.id = id;
        this.id_cv_base = id_cv_base;
        this.empresa = empresa;
        this.puesto = puesto;
        this.fecha_ini = fecha_ini;
        this.fecha_fin = fecha_fin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cv_Base getId_cv() {
        return id_cv_base;
    }

    public void setId_cv(Cv_Base id_cv) {
        this.id_cv_base = id_cv_base;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public LocalDate getFecha_ini() {
        return fecha_ini;
    }

    public void setFecha_ini(LocalDate fecha_ini) {
        this.fecha_ini = fecha_ini;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    @Override
    public String toString() {
        return "Experiencia{" +
                "id=" + id +
                ", id_cv=" + id_cv_base +
                ", empresa='" + empresa + '\'' +
                ", puesto=" + puesto +
                ", fecha_ini=" + fecha_ini +
                ", fecha_fin=" + fecha_fin +
                '}';
    }

}
