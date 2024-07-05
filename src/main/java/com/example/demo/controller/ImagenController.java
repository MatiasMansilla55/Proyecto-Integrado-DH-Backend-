package com.example.demo.controller;

import com.example.demo.dto.entrada.imagen.ImagenEntradaDto;
import com.example.demo.dto.salida.imagen.ImagenSalidaDto;
import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.entity.Imagen;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.IImagenService;
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
@RequestMapping("/imagenes")
public class ImagenController {


    public IImagenService imagenService;

    public ImagenController(IImagenService imagenService) {
        this.imagenService = imagenService;
    }

    @Operation(summary = "Registro de una nueva imagen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagen guardada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImagenSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<ImagenSalidaDto> guardar(@RequestBody @Valid ImagenEntradaDto imagen) {
        return new ResponseEntity<>(imagenService.registrarImagen(imagen), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todas las imagenes almacenadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagenes obtenidas correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImagenSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<List<ImagenSalidaDto>> listarImagenes() {
        return new ResponseEntity<>(imagenService.listarImagenes(), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarImagen(@PathVariable Long id) throws ResourceNotFoundException {
        imagenService.eliminarImagenByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
