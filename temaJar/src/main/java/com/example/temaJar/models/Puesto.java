package com.example.temaJar.models;


import com.example.temaJar.enumeracion.Categoria;
import com.example.temaJar.enumeracion.NivelDeExperiencia;
import com.example.temaJar.enumeracion.Rol;
import jakarta.persistence.*;

@Entity
@Table(name="puesto")
public class Puesto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private NivelDeExperiencia nivel;

    public Puesto() {
    }

    public Puesto(Long id, String nombre, Categoria categoria, NivelDeExperiencia nivel) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.nivel = nivel;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public NivelDeExperiencia getNivel() {
        return nivel;
    }

    public void setNivel(NivelDeExperiencia nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Puesto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria=" + categoria +
                ", nivel=" + nivel +
                '}';
    }
}
