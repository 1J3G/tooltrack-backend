package com.tooltrack.backend.repository;

import com.tooltrack.backend.model.Herramienta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HerramientaRepository extends JpaRepository<Herramienta, Long> {
    List<Herramienta> findByEstado(Herramienta.Estado estado);
    List<Herramienta> findByNombreContainingIgnoreCase(String nombre);
}