package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.entrada.usuario.UsuarioEntradaDto;
import com.example.demo.dto.modificacion.usuario.UsuarioModificacionEntradaDto;
import com.example.demo.dto.salida.usuario.UsuarioSalidaDto;

import com.example.demo.entity.Usuario;

import com.example.demo.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    UsuarioSalidaDto registrarUsuario(UsuarioEntradaDto usuario);
    List<UsuarioSalidaDto> listarUsuarios();

    UsuarioSalidaDto buscarUsuarioPorId(Long id)throws ResourceNotFoundException;

    void eliminarUsuario(Long id) throws ResourceNotFoundException;


    UsuarioSalidaDto actualizarUsuario( UsuarioModificacionEntradaDto usuario,Long id);

    UsuarioSalidaDto login(LoginRequest loginRequest, HttpSession session);

    UsuarioSalidaDto logout(HttpSession session);

    UsuarioSalidaDto usuarioEnSession();
}
