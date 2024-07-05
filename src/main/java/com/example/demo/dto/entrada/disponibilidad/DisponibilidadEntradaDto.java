package com.example.demo.dto.entrada.disponibilidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;

public class DisponibilidadEntradaDto {
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    private String pais;


    public DisponibilidadEntradaDto(LocalDate fechaInicio, LocalDate fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public DisponibilidadEntradaDto(LocalDate fechaInicio, LocalDate fechaFin, String pais) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pais = pais;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "DisponibilidadEntradaDto{" +
                "fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", pais='" + pais + '\'' +
                '}';
    }
}
