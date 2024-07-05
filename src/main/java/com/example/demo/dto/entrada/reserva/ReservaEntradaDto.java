package com.example.demo.dto.entrada.reserva;


import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.dto.salida.usuario.UsuarioSalidaDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class ReservaEntradaDto {
    private Long usuarioId;
    private Long productoId;
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    private String estado;
    private double precio_total;

    public ReservaEntradaDto() {
    }

    public ReservaEntradaDto(Long usuarioId, Long productoId, LocalDate fechaInicio, LocalDate fechaFin, String estado, double precio_total) {
        this.usuarioId = usuarioId;
        this.productoId = productoId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.precio_total = precio_total;
    }


}
