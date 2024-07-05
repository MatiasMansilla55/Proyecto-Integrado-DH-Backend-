package com.example.demo.service;

import com.example.demo.dto.entrada.disponibilidad.DisponibilidadEntradaDto;
import com.example.demo.dto.entrada.reserva.ReservaEntradaDto;
import com.example.demo.dto.salida.reserva.ReservaSalidaDto;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IReservaService {
    ReservaSalidaDto registrarReserva(ReservaEntradaDto reserva) throws BadRequestException, ResourceNotFoundException;
    List<ReservaSalidaDto> listarReservas();
    List<ReservaSalidaDto> obtenerReservasPorUsuario();

}
