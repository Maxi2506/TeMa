package com.example.temaJar.models;

import com.example.temaJar.enumeracion.Habilidad;
import jakarta.persistence.*;

@Entity
@Table(name="habilidades")
public class Habilidades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Habilidad nombre;

    public Habilidades() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Habilidad getNombre() {
        return nombre;
    }

    public void setNombre(Habilidad nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Habilidad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
