package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nombre;
    @Column(length = 200)
    private String descripcion;
    @Column()
    private Integer capacidad;
    @Column()
    private Double precioNoche;

    @Column()
    private Date disponibilidad_Desde;

    @Column()
    private Date disponibilidad_Hasta;
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST )
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    @OneToMany(cascade=CascadeType.ALL )
    @JoinColumn(name="producto_id",referencedColumnName="id")
    private List<Imagen> imagenes;

    @ManyToMany()
    @JoinTable(
            name="categoria_has_producto",
            joinColumns = @JoinColumn(name="producto_id"),
            inverseJoinColumns = @JoinColumn(name="categoria_id")
    )
    private List<Categoria> categorias;

    @ManyToMany()
    @JoinTable(
            name="producto_has_caracteristica",
            joinColumns = @JoinColumn(name="producto_id"),
            inverseJoinColumns = @JoinColumn(name="caracteristica_id")
    )
    private List<Caracteristica> caracteristicas;

    public Producto() {
    }

    public Producto(Long id, String nombre, String descripcion, Integer capacidad, Double precioNoche, Date disponibilidadDesde, Date disponibilidadHasta, Ubicacion ubicacion, List<Imagen> imagenes, List<Categoria> categorias, List<Caracteristica> caracteristicas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.precioNoche = precioNoche;
        this.disponibilidad_Desde = disponibilidadDesde;
        this.disponibilidad_Hasta = disponibilidadHasta;
        this.ubicacion = ubicacion;
        this.imagenes = imagenes;
        this.categorias = categorias;
        this.caracteristicas = caracteristicas;
    }


    public Double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(Double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Double getPrecio_noche() {
        return precioNoche;
    }

    public void setPrecio_noche(Double precio_noche) {
        this.precioNoche = precio_noche;
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

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", capacidad= " + capacidad +
                ", precio_noche= " + precioNoche +
                '}';
    }
}
