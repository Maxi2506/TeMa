package com.example.temaJar.dtos;

public class LoginDTO {
    private String correo;
    private String clave;

    public LoginDTO() {}

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
