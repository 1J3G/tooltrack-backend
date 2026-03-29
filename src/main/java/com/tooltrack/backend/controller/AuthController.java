package com.tooltrack.backend.controller;

import com.tooltrack.backend.dto.LoginRequest;
import com.tooltrack.backend.dto.LoginResponse;
import com.tooltrack.backend.dto.UsuarioDTO;
import com.tooltrack.backend.model.Usuario;
import com.tooltrack.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // Público — registra solo empleados
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(authService.registrarEmpleado(dto));
    }

    // Solo Admin — puede crear usuarios con cualquier rol
    @PostMapping("/admin/register")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Usuario> registerPorAdmin(@RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(authService.registrarPorAdmin(dto));
    }
}