package com.example.demo.dto.modificacion.imagen;

import jakarta.validation.constraints.NotNull;

public class ImagenModificacionEntradaDto {
    @NotNull(message = "Debe proveerse el id de la imagen que se desea modificar")
    private Long id;
    @NotNull(message = "Debe proveerse el nombre de la imagen que se desea modificar")
    private String nombre;

    @NotNull(message = "Debe proveerse la ruta de la imagen que se desea modificar")
    private String rutaDeArchivo;

    public ImagenModificacionEntradaDto() {
    }

    public ImagenModificacionEntradaDto(Long id,String nombre, String rutaDeArchivo) {
        this.id = id;
        this.nombre = nombre;
        this.rutaDeArchivo = rutaDeArchivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRutaDeArchivo() {
        return rutaDeArchivo;
    }

    public void setRutaDeArchivo(String rutaDeArchivo) {
        this.rutaDeArchivo = rutaDeArchivo;
    }

    @Override
    public String toString() {
        return "ImagenesEntradaDto{" +
                "nombre='" + nombre + '\'' +
                ", rutaDeArchivo='" + rutaDeArchivo + '\'' +
                '}';
    }
}
