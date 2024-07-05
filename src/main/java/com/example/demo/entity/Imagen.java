package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String nombre;
    @Column()
    private String rutaDeArchivo;


    public Imagen() {
    }


    public Imagen(Long id, String nombre, String rutaDeArchivo, Producto producto_id) {
        this.id = id;
        this.nombre = nombre;
        this.rutaDeArchivo = rutaDeArchivo;
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


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return "Imagenes{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ruta_de_la_imagen='" + rutaDeArchivo + '\'' +
                ", id alojamiento de imagen: " +
                '}';
    }
}
