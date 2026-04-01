package com.example.temaJar.dtos;

public class UsuarioDTO {
    private String nombre;
    private String apellido;
    private int edad;
    private String correo;
    private String clave; // Unificado con el nombre del JSON
    private Long telefono;
    private String domicilio;
    private String rol;
    private String localidad;
    private String provincia;
    private String pais;

    public UsuarioDTO() {
    }

    // Constructor completo
    public UsuarioDTO(String nombre, String apellido, int edad, String correo, String clave, Long telefono, String domicilio, String rol, String localidad, String provincia, String pais) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.correo = correo;
        this.clave = clave;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.rol = rol;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
    }

    // Getters y Setters corregidos
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Long getTelefono() { return telefono; }
    public void setTelefono(Long telefono) { this.telefono = telefono; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }

    public String getProvincia() { return provincia; }
    // CORRECCIÓN AQUÍ: nombre del parámetro en minúscula y asignación correcta
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
}