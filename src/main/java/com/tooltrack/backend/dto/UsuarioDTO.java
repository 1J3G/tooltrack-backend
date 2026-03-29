package com.tooltrack.backend.dto;

import com.tooltrack.backend.model.Usuario;

public class UsuarioDTO {

    private String nombre;
    private String correo;
    private String contrasena;
    private Usuario.Rol rol;

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
    public Usuario.Rol getRol() { return rol; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setRol(Usuario.Rol rol) { this.rol = rol; }
}