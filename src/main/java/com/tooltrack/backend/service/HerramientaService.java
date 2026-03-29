package com.tooltrack.backend.service;

import com.tooltrack.backend.dto.HerramientaDTO;
import com.tooltrack.backend.exception.ResourceNotFoundException;
import com.tooltrack.backend.model.Herramienta;
import com.tooltrack.backend.repository.HerramientaRepository;
import com.tooltrack.backend.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HerramientaService {

    @Autowired
    private HerramientaRepository herramientaRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    public List<Herramienta> listarTodas() {
        return herramientaRepository.findAll();
    }

    public List<Herramienta> listarDisponibles() {
        return herramientaRepository.findByEstado(Herramienta.Estado.DISPONIBLE);
    }

    public Herramienta buscarPorId(Long id) {
        return herramientaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Herramienta no encontrada con ID: " + id));
    }

    public Herramienta crear(HerramientaDTO dto) {
        if (dto.getCantidadTotal() <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad total debe ser mayor a 0");
        }

        Herramienta herramienta = new Herramienta();
        herramienta.setNombre(dto.getNombre());
        herramienta.setTipo(dto.getTipo());
        herramienta.setDescripcion(dto.getDescripcion());
        herramienta.setCantidadTotal(dto.getCantidadTotal());
        herramienta.setCantidadDisponible(dto.getCantidadTotal());
        herramienta.setCantidadPrestada(0);
        herramienta.setEstado(Herramienta.Estado.DISPONIBLE);
        return herramientaRepository.save(herramienta);
    }

    public Herramienta actualizar(Long id, HerramientaDTO dto) {
        Herramienta herramienta = buscarPorId(id);

        int diferencia = dto.getCantidadTotal() - herramienta.getCantidadTotal();
        int nuevaDisponible = herramienta.getCantidadDisponible() + diferencia;

        if (nuevaDisponible < 0) {
            throw new IllegalArgumentException(
                    "No se puede reducir el total por debajo de las unidades prestadas ("
                            + herramienta.getCantidadPrestada() + " prestadas actualmente)");
        }

        herramienta.setNombre(dto.getNombre());
        herramienta.setTipo(dto.getTipo());
        herramienta.setDescripcion(dto.getDescripcion());
        herramienta.setCantidadTotal(dto.getCantidadTotal());
        herramienta.setCantidadDisponible(nuevaDisponible);
        herramienta.setEstado(nuevaDisponible > 0
                ? Herramienta.Estado.DISPONIBLE
                : Herramienta.Estado.AGOTADA);

        return herramientaRepository.save(herramienta);
    }

    public void eliminar(Long id) {
        Herramienta herramienta = buscarPorId(id);

        if (herramienta.getCantidadPrestada() > 0) {
            throw new IllegalArgumentException(
                    "No se puede eliminar una herramienta con unidades prestadas actualmente");
        }

        if (prestamoRepository.existsByHerramientaId(id)) {
            throw new IllegalArgumentException(
                    "No se puede eliminar una herramienta que tiene historial de préstamos");
        }

        herramientaRepository.delete(herramienta);
    }

    // Método interno — llamado por PrestamoService al prestar
    public void registrarSalida(Herramienta herramienta) {
        if (herramienta.getCantidadDisponible() <= 0) {
            throw new IllegalArgumentException(
                    "No hay unidades disponibles de: " + herramienta.getNombre());
        }
        herramienta.setCantidadDisponible(herramienta.getCantidadDisponible() - 1);
        herramienta.setCantidadPrestada(herramienta.getCantidadPrestada() + 1);
        if (herramienta.getCantidadDisponible() == 0) {
            herramienta.setEstado(Herramienta.Estado.AGOTADA);
        }
        herramientaRepository.save(herramienta);
    }

    // Método interno — llamado por PrestamoService al devolver
    public void registrarEntrada(Herramienta herramienta) {
        herramienta.setCantidadDisponible(herramienta.getCantidadDisponible() + 1);
        herramienta.setCantidadPrestada(herramienta.getCantidadPrestada() - 1);
        herramienta.setEstado(Herramienta.Estado.DISPONIBLE);
        herramientaRepository.save(herramienta);
    }
}