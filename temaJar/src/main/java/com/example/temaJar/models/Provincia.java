package com.example.temaJar.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="provincia")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nombre")
    private String nombre;

    // --- NUEVA RELACIÓN: Una provincia pertenece a UN país ---
    @ManyToOne
    @JoinColumn(name = "id_pais")
    private Pais pais;

    @OneToMany(mappedBy = "provincia")
    @JsonIgnore
    private List<Usuario> clientes = new ArrayList<>();

    public Provincia() {
    }

    // Constructor actualizado
    public Provincia(Long id, String nombre, Pais pais, List<Usuario> clientes) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.clientes = clientes;
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

    // Getter y Setter para la nueva relación
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Usuario> getClientes() {
        return clientes;
    }

    public void setClientes(List<Usuario> clientes) {
        this.clientes = clientes;
    }

    @Override
    public String toString() {
        return "Provincia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", pais=" + (pais != null ? pais.getNombre() : "null") +
                '}';
    }
}
