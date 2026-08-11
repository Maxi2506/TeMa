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
    private Long id;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_provincia")
    private Provincia provincia;

    @OneToMany(mappedBy = "localidad")
    @JsonIgnore
    private List<Usuario> clientes = new ArrayList<>();

    public Localidad() {
    }

    public Localidad(Long id, String nombre, Provincia provincia, List<Usuario> clientes) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
        this.clientes = clientes;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Provincia getProvincia() { return provincia; }
    public void setProvincia(Provincia provincia) { this.provincia = provincia; }

    public List<Usuario> getClientes() { return clientes; }
    public void setClientes(List<Usuario> clientes) { this.clientes = clientes; }

    @Override
    public String toString() {
        return "Localidad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", provincia=" + (provincia != null ? provincia.getNombre() : "null") +
                '}';
    }
}