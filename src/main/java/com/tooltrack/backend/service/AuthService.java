package com.tooltrack.backend.service;

import com.tooltrack.backend.dto.LoginRequest;
import com.tooltrack.backend.dto.LoginResponse;
import com.tooltrack.backend.dto.UsuarioDTO;
import com.tooltrack.backend.exception.ResourceNotFoundException;
import com.tooltrack.backend.model.Usuario;
import com.tooltrack.backend.repository.UsuarioRepository;
import com.tooltrack.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol().name());
        return new LoginResponse(
                usuario.getId(),
                token,
                usuario.getNombre(),
                usuario.getRol().name()
        );
    }

    // Registro público — solo crea EMPLEADO
    public Usuario registrarEmpleado(UsuarioDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setRol(Usuario.Rol.EMPLEADO); // siempre EMPLEADO
        return usuarioRepository.save(usuario);
    }

    // Solo ADMIN puede crear otros usuarios con cualquier rol
    public Usuario registrarPorAdmin(UsuarioDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setRol(dto.getRol()); // el admin elige el rol
        return usuarioRepository.save(usuario);
    }
}