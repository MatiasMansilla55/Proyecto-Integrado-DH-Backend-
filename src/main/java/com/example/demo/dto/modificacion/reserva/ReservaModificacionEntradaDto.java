package com.example.demo.dto.modificacion.reserva;


import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.dto.salida.usuario.UsuarioSalidaDto;
import com.example.demo.entity.Producto;
import com.example.demo.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReservaModificacionEntradaDto {
    @NotNull(message = "Debe proveerse el id de la reserva que se desea modificar")
    private Long id;
    private ProductoSalidaDto productoSalidaDto;
    private UsuarioSalidaDto usuarioSalidaDto;
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    private String estado;
    private double precio_total;

    public ReservaModificacionEntradaDto() {
    }

    public ReservaModificacionEntradaDto(Long id, ProductoSalidaDto productoSalidaDto, UsuarioSalidaDto usuarioSalidaDto, LocalDate fechaInicio, LocalDate fechaFin, String estado, double precio_total) {
        this.id = id;
        this.productoSalidaDto = productoSalidaDto;
        this.usuarioSalidaDto = usuarioSalidaDto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.precio_total = precio_total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductoSalidaDto getProductoSalidaDto() {
        return productoSalidaDto;
    }

    public void setProductoSalidaDto(ProductoSalidaDto productoSalidaDto) {
        this.productoSalidaDto = productoSalidaDto;
    }

    public UsuarioSalidaDto getUsuarioSalidaDto() {
        return usuarioSalidaDto;
    }

    public void setUsuarioSalidaDto(UsuarioSalidaDto usuarioSalidaDto) {
        this.usuarioSalidaDto = usuarioSalidaDto;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(double precio_total) {
        this.precio_total = precio_total;
    }

    @Override
    public String toString() {
        return "ReservaModificacionEntradaDto{" +
                "id=" + id +
                ", productoSalidaDto=" + productoSalidaDto +
                ", usuarioSalidaDto=" + usuarioSalidaDto +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado='" + estado + '\'' +
                ", precio_total=" + precio_total +
                '}';
    }
}
