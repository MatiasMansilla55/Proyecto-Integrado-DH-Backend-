package com.example.demo.controller;

import com.example.demo.dto.entrada.categoria.CategoriaEntradaDto;
import com.example.demo.dto.modificacion.categoria.CategoriaModificacionEntradaDto;
import com.example.demo.dto.salida.categoria.CategoriaSalidaDto;
import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.exceptions.ResourceNotFoundException;
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
@RequestMapping("/categorias")
public class CategoriaController {


    public ICategoriaService categoriaService;

    public CategoriaController(ICategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Registro de una nueva Categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria guardado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<CategoriaSalidaDto> guardarCategoria(@RequestBody @Valid CategoriaEntradaDto categoria) {
        return new ResponseEntity<>(categoriaService.registrarCategoria(categoria), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> eliminarCategoria(@PathVariable Long  id) throws ResourceNotFoundException {
        categoriaService.eliminarCategoriaByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/listar")
    ResponseEntity<List<CategoriaSalidaDto>> listarCategorias() {
        return new ResponseEntity<>(categoriaService.listarCategorias(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarCategoria(@PathVariable Long id, @RequestBody @Valid CategoriaModificacionEntradaDto categoria) throws ResourceNotFoundException {
        categoriaService.actualizarCategoriaByID(id, categoria);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
