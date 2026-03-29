package com.tooltrack.backend.dto;

public class HerramientaDTO {

    private String nombre;
    private String tipo;
    private String descripcion;
    private int cantidadTotal;

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public int getCantidadTotal() { return cantidadTotal; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setCantidadTotal(int cantidadTotal) { this.cantidadTotal = cantidadTotal; }
}