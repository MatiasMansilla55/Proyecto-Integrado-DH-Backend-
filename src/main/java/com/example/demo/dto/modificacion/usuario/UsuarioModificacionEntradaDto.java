package com.example.demo.dto.modificacion.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioModificacionEntradaDto {
    @NotNull(message = "Debe proveerse el id del usurio que se desea modificar")
    private Long id;
    @NotNull(message = "Debe proveerse el nombre del usuario que se desea modificar")
    @NotBlank(message = "Debe especificarse el nombre del usuario")
    private String nombre;
    @NotNull(message = "Debe proveerse el apellido del usuario que se desea modificar")
    @NotBlank(message = "Debe especificarse el rol del usuario")
    private String apellido;
    @NotNull(message = "El rol del usuario no puede ser nulo")
    @NotBlank(message = "Debe especificarse el rol del usuario")
    private String rol;
    @NotNull(message = "El email del usuario no puede ser nulo")
    @NotBlank(message = "Debe especificarse el email del usuario")
    private String email;
    @NotNull(message = "La contraseña del usuario no puede ser nulo")
    @NotBlank(message = "Debe especificarse la contraseña del usuario")
    private String contrasenia;
    //@NotNull(message = "El rol del usuario no puede ser nulo")
    //@NotBlank(message = "Debe especificarse el rol del usuario")
    //private Roles roles;
    public UsuarioModificacionEntradaDto() {
    }


    @Override
    public String toString() {
        return "UsuarioModificacionEntradaDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", rol='" + rol + '\'' +
                ", email='" + email + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }
}
