package com.example.temaJar.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="cv_archivo")
// Quitamos @Data, @NoArgsConstructor y @AllArgsConstructor porque están fallando en la compilación
public class Cv_Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name="nombre_archivo")
    private String nombreArchivo;

    @Column(name="fecha_carga")
    private LocalDate fechaCarga;

    @Lob
    @Column(name="texto_extraido", columnDefinition = "LONGTEXT")
    private String textoExtraido;

    // --- CONSTRUCTORES MANUALES ---
    public Cv_Archivo() {
    }

    public Cv_Archivo(Long id, Usuario usuario, String nombreArchivo, LocalDate fechaCarga, String textoExtraido) {
        this.id = id;
        this.usuario = usuario;
        this.nombreArchivo = nombreArchivo;
        this.fechaCarga = fechaCarga;
        this.textoExtraido = textoExtraido;
    }

    // --- GETTERS Y SETTERS MANUALES (Esto arregla los 10 errores) ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public LocalDate getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(LocalDate fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getTextoExtraido() {
        return textoExtraido;
    }

    public void setTextoExtraido(String textoExtraido) {
        this.textoExtraido = textoExtraido;
    }

    @Override
    public String toString() {
        return "Cv_Archivo{" +
                "id=" + id +
                ", usuario=" + (usuario != null ? usuario.getNombre() : "null") +
                ", nombreArchivo='" + nombreArchivo + '\'' +
                ", fechaCarga=" + fechaCarga +
                ", textoExtraidoChars=" + (textoExtraido != null ? textoExtraido.length() : 0) +
                '}';
    }
}