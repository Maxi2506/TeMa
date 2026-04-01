package com.example.temaJar.models;

/*import com.sun.source.doctree.RawTextTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.DocTreeVisitor;*/
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="cv_Archivo")
public class Cv_Archivo {

    @jakarta.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "id_nombre_usuario")
    private Usuario nombre_usuario;

    @Column(name="nombre_archivo")
    private String nombre_archivo;

    @Column(name="fecha_carga")
    private LocalDate fecha_carga;

    @Column(name="texto_extraido")
    private String texto_extraido; //revisar uso y extacción de texto con ia

    public Cv_Archivo() {
    }

    public Cv_Archivo(Long id, Usuario nombre_usuario, String nombre_archivo, LocalDate fecha_carga, String texto_extraido) {
        Id = id;
        this.nombre_usuario = nombre_usuario;
        this.nombre_archivo = nombre_archivo;
        this.fecha_carga = fecha_carga;
        this.texto_extraido = texto_extraido;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Usuario getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(Usuario nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public LocalDate getFecha_carga() {
        return fecha_carga;
    }

    public void setFecha_carga(LocalDate fecha_carga) {
        this.fecha_carga = fecha_carga;
    }

    public String getTexto_extraido() {
        return texto_extraido;
    }

    public void setTexto_extraido(String texto_extraido) {
        this.texto_extraido = texto_extraido;
    }

    @Override
    public String toString() {
        return "Cv_Archivo{" +
                "Id=" + Id +
                ", nombre_usuario=" + nombre_usuario +
                ", nombre_archivo='" + nombre_archivo + '\'' +
                ", fecha_carga=" + fecha_carga +
                ", texto_extraido=" + texto_extraido +
                '}';
    }
}
