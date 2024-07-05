package com.example.demo.dto.entrada.producto;


import com.example.demo.dto.entrada.imagen.ImagenEntradaDto;
import com.example.demo.dto.entrada.ubicacion.UbicacionEntradaDto;
import com.example.demo.entity.Caracteristica;
import com.example.demo.entity.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

public class ProductoEntradaDto {

    @NotNull(message = "El nombre del producto no puede ser nulo")
    @NotEmpty(message = "Name may not be empty")
    @NotBlank(message = "Name may not be blank")
    private String nombre;

    @NotNull(message = "La descripcion del producto no puede ser nulo")
    private String descripcion;


    private Double precioNoche;

    private int capacidad;


    private Date disponibilidad_Desde;


    private Date disponibilidad_Hasta;

    private UbicacionEntradaDto ubicacion;
    private List<ImagenEntradaDto> imagenes;

    private List<Categoria> categorias;

    private List<Caracteristica> caracteristicas;


    public ProductoEntradaDto() {
    }

    public String getNombre() {
        return nombre;
    }

    public ProductoEntradaDto(String nombre, String descripcion, Double precioNoche, int capacidad, Date disponibilidadDesde, Date disponibilidadHasta, UbicacionEntradaDto ubicacion, List<ImagenEntradaDto> imagenes, List<Categoria> categorias, List<Caracteristica> caracteristicas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioNoche = precioNoche;
        this.capacidad = capacidad;
        this.disponibilidad_Desde = disponibilidadDesde;
        this.disponibilidad_Hasta = disponibilidadHasta;
        this.ubicacion = ubicacion;
        this.imagenes = imagenes;
        this.categorias = categorias;
        this.caracteristicas = caracteristicas;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Double getPrecio() {
        return precioNoche;
    }

    public Double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(Double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setImagenes(List<ImagenEntradaDto> imagenes) {
        this.imagenes = imagenes;
    }

    public void setPrecio(Double precio) {
        this.precioNoche = precio;
    }

    public List<ImagenEntradaDto> getImagenes() {
        return imagenes;
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


    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Date getDisponibilidad_Desde() {
        return disponibilidad_Desde;
    }

    public void setDisponibilidad_Desde(Date disponibilidad_Desde) {
        this.disponibilidad_Desde = disponibilidad_Desde;
    }

    public Date getDisponibilidad_Hasta() {
        return disponibilidad_Hasta;
    }

    public void setDisponibilidad_Hasta(Date disponibilidad_Hasta) {
        this.disponibilidad_Hasta = disponibilidad_Hasta;
    }

    public UbicacionEntradaDto getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionEntradaDto ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "ProductoEntradaDto{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
