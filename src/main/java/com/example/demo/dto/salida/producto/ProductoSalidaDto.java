package com.example.demo.dto.salida.producto;

import com.example.demo.dto.salida.caracteristica.CaracteristicaSalidaDto;
import com.example.demo.dto.salida.categoria.CategoriaSalidaDto;
import com.example.demo.dto.salida.imagen.ImagenSalidaDto;
import com.example.demo.dto.salida.ubicacion.UbicacionSalidaDto;
import com.example.demo.entity.Ubicacion;

import java.util.Date;
import java.util.List;

public class ProductoSalidaDto {
    private Long id;
    private String nombre;
    private String descripcion;

    private Integer capacidad;

    private Double precioNoche;

    private Date disponibilidad_Desde;

    private Date disponibilidad_Hasta;

    private UbicacionSalidaDto ubicacion;
    private List<ImagenSalidaDto> imagenes;

    private List<CategoriaSalidaDto> categorias;

    private List<CaracteristicaSalidaDto> caracteristicas;

    public ProductoSalidaDto() {
    }

    public ProductoSalidaDto(Long id, String nombre, String descripcion, Integer capacidad, Double precioNoche, UbicacionSalidaDto ubicacion, Date disponibilidadDesde, Date disponibilidadHasta, List<ImagenSalidaDto> imagenes, List<CategoriaSalidaDto> categorias, List<CaracteristicaSalidaDto> caracteristicas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.precioNoche = precioNoche;
        this.ubicacion = ubicacion;
        disponibilidad_Desde = disponibilidadDesde;
        disponibilidad_Hasta = disponibilidadHasta;
        this.imagenes = imagenes;
        this.categorias = categorias;
        this.caracteristicas = caracteristicas;
    }


    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(Double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public UbicacionSalidaDto getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionSalidaDto ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<CategoriaSalidaDto> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaSalidaDto> categorias) {
        this.categorias = categorias;
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

    public List<ImagenSalidaDto> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenSalidaDto> rutasImagenes) {
        this.imagenes = rutasImagenes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<CaracteristicaSalidaDto> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<CaracteristicaSalidaDto> caracteristicas) {
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

    @Override
    public String toString() {
        return "ProductoSalidaDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", rutasImagenes=" + imagenes +
                '}';
    }
}
