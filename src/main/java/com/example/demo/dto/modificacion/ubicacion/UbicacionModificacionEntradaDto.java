package com.example.demo.dto.modificacion.ubicacion;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class UbicacionModificacionEntradaDto {
    @NotNull(message = "Debe proveerse el id de la ubicacion que se desea modificar")
    private Long id;
    @NotNull(message = "Debe proveerse el pais de la ubicacion que se desea modificar")
    private String pais;
    @NotNull(message = "Debe proveerse la ciudad de la ubicacion que se desea modificar")
    private String ciudad;
    @NotNull(message = "Debe proveerse el codigo postal de la ubicacion que se desea modificar")
    private int codigoPostal;
    @NotNull(message = "Debe proveerse la direccion exacta de la ubicacion que se desea modificar")
    private String direccionExacta;


    public UbicacionModificacionEntradaDto() {
    }

    public UbicacionModificacionEntradaDto(Long id, String pais, String ciudad, int codigoPostal, String direccionExacta) {
        this.id = id;
        this.pais = pais;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.direccionExacta = direccionExacta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getDireccionExacta() {
        return direccionExacta;
    }

    public void setDireccionExacta(String direccionExacta) {
        this.direccionExacta = direccionExacta;
    }


    @Override
    public String toString() {
        return "UbicacionModificacionEntradaDto{" +
                "pais='" + pais + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", codigoPostal=" + codigoPostal +
                ", direccionExacta='" + direccionExacta + '\'' +
                '}';
    }
}
