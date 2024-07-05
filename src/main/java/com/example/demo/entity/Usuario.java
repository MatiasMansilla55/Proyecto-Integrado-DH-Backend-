package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nombre;
    @Column(length = 50)
    private String apellido;

    @ManyToOne()
    @JoinColumn(name="rol_id")
    private Rol roles;
    @Column(length = 50)
    private String email;
    @Column(length = 500)
    private String contrasenia;
    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas;

    @Column(length = 50)
    private String rol;

    public Usuario() {
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", roles=" + roles +
                ", email='" + email + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", reservas=" + reservas +
                ", rol='" + rol + '\'' +
                '}';
    }
}
