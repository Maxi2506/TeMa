package com.example.temaJar.models;

import jakarta.persistence.*;

@Entity
@Table(name="habilidades")
public class Habilidades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nombre")
    private String nombre;

    public Habilidades() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Habilidades{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
