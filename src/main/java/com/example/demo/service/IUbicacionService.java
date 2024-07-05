package com.example.demo.service;

import com.example.demo.dto.entrada.ubicacion.UbicacionEntradaDto;
import com.example.demo.dto.salida.ubicacion.UbicacionSalidaDto;

public interface IUbicacionService {
    UbicacionSalidaDto registrarUbicacion(UbicacionEntradaDto ubicacion);
}
