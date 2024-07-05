package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy="categorias")
    private List<Producto> productos;
    @Column
    private String nombre;
    @Column
    private String descripcion;

    public Categoria() {
    }

    public Categoria(Long id, List<Producto> productos, String nombre, String descripcion) {
        this.id = id;
        this.productos = productos;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

//    public Categoria(Long id, String nombre, String descripcion) {
//        this.id = id;
//        //this.alojamiento = alojamiento;
//        this.nombre = nombre;
//        this.descripcion = descripcion;
//    }

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
        return "Categoria{" +
                "id=" + id +
                //", alojamiento=" + alojamiento +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
