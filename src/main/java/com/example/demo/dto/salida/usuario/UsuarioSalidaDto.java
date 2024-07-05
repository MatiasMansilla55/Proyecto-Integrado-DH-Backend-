package com.example.demo.dto.salida.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioSalidaDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String rol;
    //private Roles roles;

    public UsuarioSalidaDto() {
    }


    @Override
    public String toString() {
        return "UsuarioSalidaDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
