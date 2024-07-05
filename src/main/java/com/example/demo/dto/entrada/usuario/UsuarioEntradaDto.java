package com.example.demo.dto.entrada.usuario;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioEntradaDto {

    @NotNull(message = "El nombre del usuario no puede ser nulo")
    @NotBlank(message = "Debe especificarse el nombre del usuario")
    private String nombre;
    @NotNull(message = "El apellido del usuario no puede ser nulo")
    @NotBlank(message = "Debe especificarse el apellido del usuario")
    private String apellido;
    @NotNull(message = "El rol del usuario no puede ser nulo")
    @NotBlank(message = "Debe especificarse el rol del usuario")
    private String rol;
    @NotNull(message = "El email del usuario no puede ser nulo")
    @NotBlank(message = "Debe especificarse el email del usuario")
    private String email;
    @NotNull(message = "La contraseña del usuario no puede ser nulo")
    //@NotBlank(message = "Debe especificarse la contraseña del usuario")
    private String contrasenia;
    //@NotNull(message = "El rol del usuario no puede ser nulo")
    //@Enumerated(EnumType.STRING)
    //private Roles roles;


    public UsuarioEntradaDto() {
    }


    @Override
    public String toString() {
        return "UsuarioEntradaDto{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", rol='" + rol + '\'' +
                ", email='" + email + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }
}
