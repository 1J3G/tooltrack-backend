package com.tooltrack.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_herramienta", nullable = false)
    private Herramienta herramienta;

    @Column(nullable = false)
    private LocalDateTime fechaPrestamo;

    private LocalDateTime fechaDevolucion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    public enum Estado {
        ACTIVO, DEVUELTO
    }

    public Prestamo() {}

    public Prestamo(Long id, Usuario usuario, Herramienta herramienta,
                    LocalDateTime fechaPrestamo, LocalDateTime fechaDevolucion, Estado estado) {
        this.id = id;
        this.usuario = usuario;
        this.herramienta = herramienta;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }

    // Getters
    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Herramienta getHerramienta() { return herramienta; }
    public LocalDateTime getFechaPrestamo() { return fechaPrestamo; }
    public LocalDateTime getFechaDevolucion() { return fechaDevolucion; }
    public Estado getEstado() { return estado; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setHerramienta(Herramienta herramienta) { this.herramienta = herramienta; }
    public void setFechaPrestamo(LocalDateTime fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public void setFechaDevolucion(LocalDateTime fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public void setEstado(Estado estado) { this.estado = estado; }

    @PrePersist
    public void prePersist() {
        this.fechaPrestamo = LocalDateTime.now();
        this.estado = Estado.ACTIVO;
    }
}