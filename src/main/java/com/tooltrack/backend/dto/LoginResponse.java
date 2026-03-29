package com.tooltrack.backend.dto;

public class LoginResponse {

    private Long id;
    private String token;
    private String nombre;
    private String rol;

    public LoginResponse() {}

    public LoginResponse(Long id, String token, String nombre, String rol) {
        this.id = id;
        this.token = token;
        this.nombre = nombre;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public String getToken() { return token; }
    public String getNombre() { return nombre; }
    public String getRol() { return rol; }

    public void setId(Long id) { this.id = id; }
    public void setToken(String token) { this.token = token; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRol(String rol) { this.rol = rol; }
}