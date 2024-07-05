package com.example.demo.dto.entrada.imagen;

import com.example.demo.entity.Producto;

public class ImagenEntradaDto {
    private String nombre;
    private String rutaDeArchivo;


    public ImagenEntradaDto() {
    }

    public ImagenEntradaDto(String nombre, String rutaDeArchivo, Producto producto_id) {
        this.nombre = nombre;
        this.rutaDeArchivo = rutaDeArchivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
                ", ruta_de_la_imagen='" + rutaDeArchivo + '\'' +
                '}';
    }
}
