package com.example.demo.controller;

import com.example.demo.dto.entrada.disponibilidad.DisponibilidadEntradaDto;
import com.example.demo.dto.entrada.reserva.ReservaEntradaDto;
import com.example.demo.dto.salida.reserva.ReservaSalidaDto;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.IReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("reservas")
public class ReservaController {
    private final Logger LOGGER = LoggerFactory.getLogger(ReservaController.class);
    @Autowired
    private IReservaService reservaService;

    public ReservaController(IReservaService reservaService) {
        this.reservaService = reservaService;
    }
    @Operation(summary = "Registro de un nueva Reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva guardada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservaSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<ReservaSalidaDto> guardar(@RequestBody @Valid ReservaEntradaDto reserva) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(reservaService.registrarReserva(reserva), HttpStatus.CREATED);
    }
    @Operation(summary = "Listado de todas las Reservas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de todas las Reservas obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservaSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })


    @GetMapping("/listar")
    public ResponseEntity<List<ReservaSalidaDto>> listarReservas()  {
        List<ReservaSalidaDto> reservas = reservaService.listarReservas();
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }
    @GetMapping("/mis-reservas")
    public ResponseEntity<List<ReservaSalidaDto>> obtenerMisReservas() {
        List<ReservaSalidaDto> reservas = reservaService.obtenerReservasPorUsuario();
        return ResponseEntity.ok(reservas);
    }



}
