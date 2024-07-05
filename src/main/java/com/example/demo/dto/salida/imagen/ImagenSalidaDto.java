package com.example.demo.dto.salida.imagen;

import jakarta.validation.constraints.NotNull;

public class ImagenSalidaDto {
    @NotNull(message = "Debe proveerse el id de la imagen que se desea modificar")
    private Long id;
    @NotNull(message = "Debe proveerse el nombre de la imagen que se desea modificar")
    private String nombre;
    @NotNull(message = "Debe proveerse la ruta de la imagen que se desea modificar")
    private String rutaDeArchivo;

    public ImagenSalidaDto() {
    }

    public ImagenSalidaDto(String nombre, String tipo_de_archivo,Long id) {
        this.id = id;
        this.nombre = nombre;
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


    public String getrutaDeArchivo() {
        return rutaDeArchivo;
    }

    public void setrutaDeArchivo(String ruta_de_la_imagen) {
        this.rutaDeArchivo = ruta_de_la_imagen;
    }

    @Override
    public String toString() {
        return "ImagenesSalidaDto{" +
                "nombre='" + nombre + '\'' +
                ", ruta_de_la_imagen='" + rutaDeArchivo + '\'' +
                '}';
    }
}
