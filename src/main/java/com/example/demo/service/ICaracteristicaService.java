package com.example.demo.service;

import com.example.demo.dto.entrada.caracteristica.CaracteristicaEntradaDto;
import com.example.demo.dto.modificacion.caracteristica.CaracteristicaModificacionEntradaDto;
import com.example.demo.dto.salida.caracteristica.CaracteristicaSalidaDto;
import com.example.demo.dto.salida.categoria.CategoriaSalidaDto;

import java.util.List;

public interface ICaracteristicaService {
    CaracteristicaSalidaDto registrarCaracteristica(CaracteristicaEntradaDto caracteristica);

    Void eliminarCaracteristicaByID(Long id);

    List<CaracteristicaSalidaDto> listarCaracteristicas();

    void actualizarCaracteristicaByID(Long id, CaracteristicaModificacionEntradaDto caracteristica);
}
