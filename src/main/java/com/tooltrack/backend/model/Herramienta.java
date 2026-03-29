package com.tooltrack.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "herramientas")
public class Herramienta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(length = 255)
    private String descripcion;

    @Column(nullable = false)
    private int cantidadTotal;

    @Column(nullable = false)
    private int cantidadDisponible;

    @Column(nullable = false)
    private int cantidadPrestada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    public enum Estado {
        DISPONIBLE, AGOTADA
    }

    public Herramienta() {}

    public Herramienta(Long id, String nombre, String tipo, String descripcion,
                       int cantidadTotal, int cantidadDisponible,
                       int cantidadPrestada, Estado estado) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.cantidadTotal = cantidadTotal;
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadPrestada = cantidadPrestada;
        this.estado = estado;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public int getCantidadTotal() { return cantidadTotal; }
    public int getCantidadDisponible() { return cantidadDisponible; }
    public int getCantidadPrestada() { return cantidadPrestada; }
    public Estado getEstado() { return estado; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setCantidadTotal(int cantidadTotal) { this.cantidadTotal = cantidadTotal; }
    public void setCantidadDisponible(int cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }
    public void setCantidadPrestada(int cantidadPrestada) { this.cantidadPrestada = cantidadPrestada; }
    public void setEstado(Estado estado) { this.estado = estado; }
}