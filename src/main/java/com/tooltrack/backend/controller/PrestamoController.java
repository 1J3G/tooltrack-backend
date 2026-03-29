package com.tooltrack.backend.controller;

import com.tooltrack.backend.dto.PrestamoDTO;
import com.tooltrack.backend.dto.PrestamoResponseDTO;
import com.tooltrack.backend.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<PrestamoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    @GetMapping("/activos")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<PrestamoResponseDTO>> listarActivos() {
        return ResponseEntity.ok(prestamoService.listarActivos());
    }

    @GetMapping("/historial")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<PrestamoResponseDTO>> getHistorial() {
        return ResponseEntity.ok(prestamoService.getHistorial());
    }

    @GetMapping("/historial/usuario/{idUsuario}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<PrestamoResponseDTO>> getHistorialPorUsuario(
            @PathVariable Long idUsuario) {
        return ResponseEntity.ok(prestamoService.getHistorialPorUsuario(idUsuario));
    }

    @GetMapping("/mis-prestamos")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public ResponseEntity<List<PrestamoResponseDTO>> getMisPrestamos(
            @RequestParam Long idUsuario) {
        return ResponseEntity.ok(prestamoService.getHistorialPorUsuario(idUsuario));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PrestamoResponseDTO> registrarPrestamo(
            @RequestBody PrestamoDTO dto) {
        return ResponseEntity.ok(prestamoService.registrarPrestamo(dto));
    }

    @PutMapping("/{id}/devolver")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PrestamoResponseDTO> registrarDevolucion(
            @PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.registrarDevolucion(id));
    }
}