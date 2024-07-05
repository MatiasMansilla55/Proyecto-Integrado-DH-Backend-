package com.example.demo.controller;

import com.example.demo.dto.entrada.ubicacion.UbicacionEntradaDto;
import com.example.demo.dto.salida.ubicacion.UbicacionSalidaDto;
import com.example.demo.service.IUbicacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ubicacion")
public class UbicacionController {


    public IUbicacionService ubicacionService;

    public UbicacionController(IUbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    @Operation(summary = "Registro de una nueva Ubicacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ubicacion guardada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UbicacionSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<UbicacionSalidaDto> guardar(@RequestBody @Valid UbicacionEntradaDto ubicacion) {
        return new ResponseEntity<>(ubicacionService.registrarUbicacion(ubicacion), HttpStatus.CREATED);
    }
}
