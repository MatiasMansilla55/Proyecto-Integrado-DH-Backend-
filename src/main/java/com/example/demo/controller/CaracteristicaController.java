package com.example.demo.controller;

import com.example.demo.dto.entrada.caracteristica.CaracteristicaEntradaDto;
import com.example.demo.dto.entrada.categoria.CategoriaEntradaDto;
import com.example.demo.dto.modificacion.caracteristica.CaracteristicaModificacionEntradaDto;
import com.example.demo.dto.salida.caracteristica.CaracteristicaSalidaDto;
import com.example.demo.dto.salida.categoria.CategoriaSalidaDto;
import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.ICaracteristicaService;
import com.example.demo.service.ICategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caracteristicas")
public class CaracteristicaController {
    public ICaracteristicaService caracteristicaService;

    public CaracteristicaController(ICaracteristicaService caracteristicaService) {
        this.caracteristicaService = caracteristicaService;
    }

    @Operation(summary = "Registro de una nueva caracteristica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "caracteristica guardado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<CaracteristicaSalidaDto> guardarCaracteristica(@RequestBody @Valid CaracteristicaEntradaDto caracteristica) {
        return new ResponseEntity<>(caracteristicaService.registrarCaracteristica(caracteristica), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> eliminarCaracteristica(@PathVariable Long  id) {
        caracteristicaService.eliminarCaracteristicaByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/listar")
    ResponseEntity<List<CaracteristicaSalidaDto>> listarcaraCteristicas() {
        return new ResponseEntity<>(caracteristicaService.listarCaracteristicas(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarCaracteristica(@PathVariable Long id, @RequestBody @Valid CaracteristicaModificacionEntradaDto caracteristica) throws ResourceNotFoundException {
        caracteristicaService.actualizarCaracteristicaByID(id,caracteristica);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
