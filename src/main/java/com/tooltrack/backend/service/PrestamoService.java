package com.tooltrack.backend.service;

import com.tooltrack.backend.dto.PrestamoDTO;
import com.tooltrack.backend.dto.PrestamoResponseDTO;
import com.tooltrack.backend.exception.ResourceNotFoundException;
import com.tooltrack.backend.model.Herramienta;
import com.tooltrack.backend.model.Prestamo;
import com.tooltrack.backend.model.Usuario;
import com.tooltrack.backend.repository.HerramientaRepository;
import com.tooltrack.backend.repository.PrestamoRepository;
import com.tooltrack.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private HerramientaRepository herramientaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HerramientaService herramientaService;

    public List<PrestamoResponseDTO> listarTodos() {
        return prestamoRepository.findAll()
                .stream()
                .map(PrestamoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PrestamoResponseDTO> listarActivos() {
        return prestamoRepository.findByEstado(Prestamo.Estado.ACTIVO)
                .stream()
                .map(PrestamoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PrestamoResponseDTO> getHistorial() {
        return prestamoRepository.findAllByOrderByFechaPrestamoDesc()
                .stream()
                .map(PrestamoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PrestamoResponseDTO> getHistorialPorUsuario(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new ResourceNotFoundException(
                    "Usuario no encontrado con ID: " + idUsuario);
        }
        return prestamoRepository
                .findByUsuarioIdOrderByFechaPrestamoDesc(idUsuario)
                .stream()
                .map(PrestamoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public PrestamoResponseDTO registrarPrestamo(PrestamoDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Herramienta herramienta = herramientaRepository.findById(dto.getIdHerramienta())
                .orElseThrow(() -> new ResourceNotFoundException("Herramienta no encontrada"));

        // Descuenta del inventario (valida disponibilidad internamente)
        herramientaService.registrarSalida(herramienta);

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setHerramienta(herramienta);

        return PrestamoResponseDTO.fromEntity(prestamoRepository.save(prestamo));
    }

    public PrestamoResponseDTO registrarDevolucion(Long idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado"));

        if (prestamo.getEstado() == Prestamo.Estado.DEVUELTO) {
            throw new IllegalArgumentException("Este préstamo ya fue devuelto");
        }

        prestamo.setEstado(Prestamo.Estado.DEVUELTO);
        prestamo.setFechaDevolucion(LocalDateTime.now());

        // Devuelve al inventario
        herramientaService.registrarEntrada(prestamo.getHerramienta());

        return PrestamoResponseDTO.fromEntity(prestamoRepository.save(prestamo));
    }
}