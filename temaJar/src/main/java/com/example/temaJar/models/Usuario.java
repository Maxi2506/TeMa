package com.example.temaJar.models;

import com.example.temaJar.enumeracion.Rol;
import jakarta.persistence.*;

@Entity
@Table(name="usuario")
public class Usuario {

    private static final long serialVersionUID = 6522896498689132123L;

    @Id // Simplificado el import ya que usas jakarta.persistence.*
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="apellido")
    private String apellido;

    @Column(name="edad")
    private int edad;

    @Column(name="correo")
    private String correo;

    @Column(name="clave")
    private String clave;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(name="telefono")
    private Long telefono;

    @Column(name="domicilio")
    private String domicilio;

    @ManyToOne
    @JoinColumn(name = "id_localidad")
    private Localidad localidad;

    @ManyToOne
    @JoinColumn(name = "id_provincia")
    private Provincia provincia;

    @ManyToOne
    @JoinColumn(name = "id_pais")
    private Pais pais;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String apellido, int edad, String correo, String clave, Rol rol, Long telefono, String domicilio, Localidad localidad, Provincia provincia, Pais pais) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
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
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", correo='" + correo + '\'' +
                ", clave='" + clave + '\'' +
                ", rol=" + rol +
                ", telefono=" + telefono +
                ", domicilio='" + domicilio + '\'' +
                ", localidad=" + localidad +
                ", provincia=" + provincia +
                ", pais=" + pais +
                '}';
    }
}
