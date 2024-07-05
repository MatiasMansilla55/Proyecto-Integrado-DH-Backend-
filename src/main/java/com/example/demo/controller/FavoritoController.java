package com.example.demo.controller;

import com.example.demo.dto.entrada.favorito.FavoritoEntradaDto;
import com.example.demo.dto.salida.favorito.FavoritoSalidaDto;
import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.IFavoritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("favoritos")
public class FavoritoController {
    private final Logger LOGGER = LoggerFactory.getLogger(FavoritoController.class);

    private IFavoritoService favoritoService;

    public FavoritoController(IFavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    @Operation(summary = "Listado de todos los favoritos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de favoritos obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoritoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })


    @GetMapping("/mis-favortios")
    public ResponseEntity<List<FavoritoSalidaDto>> listarFavoritos() {
        List<FavoritoSalidaDto> favoritos = favoritoService.listarFavoritosByusuario();
        return new ResponseEntity<>(favoritos, HttpStatus.OK);
    }

    @Operation(summary = "Registro de un nuevo Favorito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Favorito guardado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoritoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<FavoritoSalidaDto> guardar(@RequestBody @Valid FavoritoEntradaDto favorito) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(favoritoService.registrarFavorito(favorito), HttpStatus.CREATED);
    }


    //buscar producto con PathVariable
    @Operation(summary = "Búsqueda de un favorito por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorito obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Favorito no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<FavoritoSalidaDto> obtenerProductoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(favoritoService.buscarFavoritoPorId(id), HttpStatus.OK);
    }

//    @Operation(summary = "Eliminación de un favorito por Id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "favorito eliminado correctamente",
//                    content = {@Content(mediaType = "application/json",
//                            schema = @Schema(implementation = String.class))}),
//            @ApiResponse(responseCode = "400", description = "Id inválido",
//                    content = @Content),
//            @ApiResponse(responseCode = "404", description = "favorito no encontrado",
//                    content = @Content),
//            @ApiResponse(responseCode = "500", description = "Server error",
//                    content = @Content)
//    })
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> eliminarFavorito(@PathVariable Long id) throws ResourceNotFoundException {
//        favoritoService.eliminarFavorito(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
