package com.example.demo.dto.entrada.caracteristica;

public class CaracteristicaEntradaDto {
    private String nombre;
    private String icono;

    public CaracteristicaEntradaDto() {
    }

    public CaracteristicaEntradaDto(String nombre, String icono) {
        this.nombre = nombre;
        this.icono = icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String tipo) {
        this.icono = tipo;
    }

    @Override
    public String toString() {
        return "CaracteristicaEntradaDto{" +
                "nombre='" + nombre + '\'' +
                ", icono='" + icono + '\'' +
                '}';
    }
}
