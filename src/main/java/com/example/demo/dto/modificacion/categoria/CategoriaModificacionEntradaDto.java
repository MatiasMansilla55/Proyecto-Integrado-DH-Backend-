package com.example.demo.dto.modificacion.categoria;

import jakarta.validation.constraints.NotNull;

public class CategoriaModificacionEntradaDto {
    @NotNull(message = "el campo id no puede esta nulo")
    private Long id;
    @NotNull(message = "el campo nombre no puede esta nulo")
    private String nombre;
    @NotNull(message = "el campo descripcion no puede esta nulo")
    private String descripcion;

    public CategoriaModificacionEntradaDto() {
    }

    public CategoriaModificacionEntradaDto(String nombre, String descripcion, Long id) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "CategoriaModificacionEntradaDto{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
