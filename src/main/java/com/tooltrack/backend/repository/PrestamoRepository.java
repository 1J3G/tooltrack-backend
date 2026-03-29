package com.tooltrack.backend.repository;

import com.tooltrack.backend.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByEstado(Prestamo.Estado estado);
    List<Prestamo> findByUsuarioId(Long usuarioId);

    // Historial completo de un usuario específico
    List<Prestamo> findByUsuarioIdOrderByFechaPrestamoDesc(Long usuarioId);

    // Historial general ordenado por fecha
    List<Prestamo> findAllByOrderByFechaPrestamoDesc();
    boolean existsByHerramientaIdAndEstado(Long herramientaId, Prestamo.Estado estado);

    // Nuevo — verifica si tiene cualquier préstamo (activo o devuelto)
    boolean existsByHerramientaId(Long herramientaId);
}