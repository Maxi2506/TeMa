package com.example.temaJar.models;

import com.example.temaJar.enumeracion.Habilidad;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name="Cv_Base")
public class Cv_Base {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name="resumen_profesional", columnDefinition = "TEXT")
    private String resumen_profesional;

    @Column(name="telefono")
    private long telefono;

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

    // --- NUEVA IMPLEMENTACIÓN CON ENUM DIRECTO ---
    @ElementCollection(targetClass = Habilidad.class)
    @CollectionTable(name = "cv_habilidades", joinColumns = @JoinColumn(name = "id_cv_base"))
    @Column(name = "habilidad", length = 50) // <--- ESTO ES LO IMPORTANTE
    @Enumerated(EnumType.STRING)
    private List<Habilidad> habilidades;

    public Cv_Base() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getResumen_profesional() { return resumen_profesional; }
    public void setResumen_profesional(String resumen_profesional) { this.resumen_profesional = resumen_profesional; }
    public long getTelefono() { return telefono; }
    public void setTelefono(long telefono) { this.telefono = telefono; }
    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }
    public String getLinkedin_url() { return linkedin_url; }
    public void setLinkedin_url(String linkedin_url) { this.linkedin_url = linkedin_url; }
    public Localidad getLocalidad() { return localidad; }
    public void setLocalidad(Localidad localidad) { this.localidad = localidad; }
    public Provincia getProvincia() { return provincia; }
    public void setProvincia(Provincia provincia) { this.provincia = provincia; }
    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }
    public List<Habilidad> getHabilidades() { return habilidades; }
    public void setHabilidades(List<Habilidad> habilidades) { this.habilidades = habilidades; }
}