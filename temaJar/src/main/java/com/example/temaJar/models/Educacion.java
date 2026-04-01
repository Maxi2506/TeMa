package com.example.temaJar.models;

import jakarta.persistence.*;

@Entity
@Table(name="educacion")
public class Educacion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cv_base")
    private Cv_Base id_cv_base;

    @Column(name="institucion")
    private String institucion;

    @Column(name="titulo")
    private String titulo;

    @Column(name="duracion")
    private int duracion;

    public Educacion() {
    }

    public Educacion(Long id, Cv_Base id_cv_base, String institucion, String titulo, int duracion) {
        this.id = id;
        this.id_cv_base = id_cv_base;
        this.institucion = institucion;
        this.titulo = titulo;
        this.duracion = duracion;
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

    @Override
    public String toString() {
        return "Educacion{" +
                "id=" + id +
                ", id_cv_base=" + id_cv_base +
                ", institucion='" + institucion + '\'' +
                ", titulo='" + titulo + '\'' +
                ", duracion=" + duracion +
                '}';
    }
}
