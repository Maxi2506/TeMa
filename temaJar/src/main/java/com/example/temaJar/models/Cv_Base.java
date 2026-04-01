package com.example.temaJar.models;

import jakarta.persistence.*;

@Entity
@Table(name="Cv_Base")
public class Cv_Base {

    @jakarta.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "id_nombre_usuario")
    private Usuario nombre_usuario;

    @Column(name="resumen_profesional")
    private String resumen_profesional;

    @Column(name="telefono")
    private int telefono;

    @Column(name="domicilio")
    private String domicilio;

    @Column(name="linkedin_url")
    private String linkedin_url;

    @ManyToOne
    @JoinColumn(name = "id_localidad")
    private Localidad localidad;

    @ManyToOne
    @JoinColumn(name = "id_provincia")
    private Provincia provincia;

    @ManyToOne
    @JoinColumn(name = "id_pais")
    private Pais pais;

    public Cv_Base() {
    }

    public Cv_Base(Long id, Usuario nombre_usuario, String resumen_profesional, int telefono, String domicilio, String linkedin_url, Localidad localidad, Provincia provincia, Pais pais) {
        Id = id;
        this.nombre_usuario = nombre_usuario;
        this.resumen_profesional = resumen_profesional;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.linkedin_url = linkedin_url;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
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

    public String getResumen_profesional() {
        return resumen_profesional;
    }

    public void setResumen_profesional(String resumen_profesional) {
        this.resumen_profesional = resumen_profesional;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLinkedin_url() {
        return linkedin_url;
    }

    public void setLinkedin_url(String linkedin_url) {
        this.linkedin_url = linkedin_url;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "Cv_Base{" +
                "Id=" + Id +
                ", nombre_usuario=" + nombre_usuario +
                ", resumen_profesional='" + resumen_profesional + '\'' +
                ", telefono=" + telefono +
                ", domicilio='" + domicilio + '\'' +
                ", linkedin_url='" + linkedin_url + '\'' +
                ", localidad=" + localidad +
                ", provincia=" + provincia +
                ", pais=" + pais +
                '}';
    }
}
