package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.salida.ubicacion.UbicacionSalidaDto;
import com.example.demo.dto.salida.usuario.UsuarioSalidaDto;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginAndContentController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<UsuarioSalidaDto> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        return new ResponseEntity<>(usuarioService.login(loginRequest, session), HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<UsuarioSalidaDto> logout(HttpSession session) {
        return new ResponseEntity<>(usuarioService.logout(session), HttpStatus.OK);
    }
}
