package com.example.demo.dto.salida.caracteristica;

public class CaracteristicaSalidaDto {
    private Long id;

    private String nombre;

    private String icono;

    public CaracteristicaSalidaDto() {
    }

    public CaracteristicaSalidaDto(Long id, String nombre, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.icono = icono;
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

    public String getIcono() {
        return icono;
    }

    public void setTIcono(String icono) {
        this.icono = icono;
    }

    @Override
    public String toString() {
        return "CaracteristicaSalidaDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", icono=" + icono+
                '}';
    }
}
