package com.example.demo.dto.modificacion.producto;

import com.example.demo.dto.modificacion.imagen.ImagenModificacionEntradaDto;
import com.example.demo.dto.modificacion.ubicacion.UbicacionModificacionEntradaDto;
import com.example.demo.entity.Caracteristica;
import com.example.demo.entity.Categoria;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

public class ProductoModificacionEntradaDto {

    @NotNull(message = "Debe proveerse el id del producto que se desea modificar")
    private Long id;
    @NotNull(message = "Debe proveerse el nombre del producto que se desea modificar")
    private String nombre;
    @NotNull(message = "Debe proveerse la descripcion del producto que se desea modificar")
    private String descripcion;
    @NotNull
    private Integer capacidad;
    @NotNull
    private Double precioNoche;

    @NotNull
    private Date disponibilidad_Desde;

    @NotNull
    private Date disponibilidad_Hasta;

//    @NotNull
//    private UbicacionModificacionEntradaDto ubicacionModificacionEntradaDto;

    @NotNull(message = "Debe proveerse las imagenes del producto que se desea modificar")
    private List<ImagenModificacionEntradaDto> imagenes;
    @NotNull
    private List<Categoria> categorias;

    private List<Caracteristica> caracteristicas;


    public ProductoModificacionEntradaDto() {
    }

    public ProductoModificacionEntradaDto(Long id, String nombre, String descripcion, Integer capacidad, Double precioNoche, Date disponibilidadDesde, Date disponibilidadHasta, UbicacionModificacionEntradaDto ubicacionModificacionEntradaDto, List<ImagenModificacionEntradaDto> imagenes, List<Categoria> categorias, List<Caracteristica> caracteristicas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.precioNoche = precioNoche;
        disponibilidad_Desde = disponibilidadDesde;
        disponibilidad_Hasta = disponibilidadHasta;
        //this.ubicacionModificacionEntradaDto = ubicacionModificacionEntradaDto;
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

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long producto_id) {
        this.id = producto_id;
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


    public List<ImagenModificacionEntradaDto> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenModificacionEntradaDto> imagenes) {
        this.imagenes = imagenes;
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

//    public UbicacionModificacionEntradaDto getUbicacionModificacionEntradaDto() {
//        return ubicacionModificacionEntradaDto;
//    }
//
//    public void setUbicacionModificacionEntradaDto(UbicacionModificacionEntradaDto ubicacionModificacionEntradaDto) {
//        this.ubicacionModificacionEntradaDto = ubicacionModificacionEntradaDto;
//    }

    @Override
    public String toString() {
        return "ProductoModificacionDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", rutasImagenes=" + imagenes +
                '}';
    }
}
