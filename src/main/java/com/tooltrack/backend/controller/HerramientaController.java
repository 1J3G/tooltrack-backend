package com.tooltrack.backend.controller;

import com.tooltrack.backend.dto.HerramientaDTO;
import com.tooltrack.backend.model.Herramienta;
import com.tooltrack.backend.service.HerramientaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/herramientas")
public class HerramientaController {

    @Autowired
    private HerramientaService herramientaService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Herramienta>> listarTodas() {
        return ResponseEntity.ok(herramientaService.listarTodas());
    }

    @GetMapping("/resumen")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public ResponseEntity<List<Herramienta>> getResumen() {
        return ResponseEntity.ok(herramientaService.listarTodas());
    }

    @GetMapping("/disponibles")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLEADO')")
    public ResponseEntity<List<Herramienta>> listarDisponibles() {
        return ResponseEntity.ok(herramientaService.listarDisponibles());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Herramienta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(herramientaService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Herramienta> crear(@RequestBody HerramientaDTO dto) {
        return ResponseEntity.ok(herramientaService.crear(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Herramienta> actualizar(@PathVariable Long id,
                                                  @RequestBody HerramientaDTO dto) {
        return ResponseEntity.ok(herramientaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        herramientaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}