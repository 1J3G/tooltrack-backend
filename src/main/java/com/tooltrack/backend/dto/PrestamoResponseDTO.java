package com.tooltrack.backend.dto;

import com.tooltrack.backend.model.Prestamo;
import java.time.LocalDateTime;

public class PrestamoResponseDTO {

    private Long id;
    private String nombreUsuario;
    private String nombreHerramienta;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucion;
    private String estado;

    // Getters
    public Long getId() { return id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getNombreHerramienta() { return nombreHerramienta; }
    public LocalDateTime getFechaPrestamo() { return fechaPrestamo; }
    public LocalDateTime getFechaDevolucion() { return fechaDevolucion; }
    public String getEstado() { return estado; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public void setNombreHerramienta(String nombreHerramienta) { this.nombreHerramienta = nombreHerramienta; }
    public void setFechaPrestamo(LocalDateTime fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public void setFechaDevolucion(LocalDateTime fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public void setEstado(String estado) { this.estado = estado; }

    public static PrestamoResponseDTO fromEntity(Prestamo p) {
        PrestamoResponseDTO dto = new PrestamoResponseDTO();
        dto.setId(p.getId());
        dto.setNombreUsuario(p.getUsuario().getNombre());
        dto.setNombreHerramienta(p.getHerramienta().getNombre());
        dto.setFechaPrestamo(p.getFechaPrestamo());
        dto.setFechaDevolucion(p.getFechaDevolucion());
        dto.setEstado(p.getEstado().name());
        return dto;
    }
}