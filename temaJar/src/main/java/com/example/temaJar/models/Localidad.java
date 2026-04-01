package com.example.temaJar.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="localidad")
public class Localidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @OneToMany(mappedBy = "localidad")
    @JsonIgnore
    private List<Usuario> clientes = new ArrayList<>();

    public Localidad() {
    }

    public Localidad(long id, String nombre, List<Usuario> clientes) {
        this.id = id;
        this.nombre = nombre;
        this.clientes = clientes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuario> getClientes() {
        return clientes;
    }

    public void setClientes(List<Usuario> clientes) {
        this.clientes = clientes;
    }

    @Override
    public String toString() {
        return "Localidad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", clientes=" + clientes +
                '}';
    }
}
