package com.example.demo.controller;

import com.example.demo.dto.entrada.disponibilidad.DisponibilidadEntradaDto;
import com.example.demo.dto.entrada.producto.ProductoEntradaDto;
import com.example.demo.dto.modificacion.producto.ProductoModificacionEntradaDto;
import com.example.demo.dto.salida.categoria.CategoriaSalidaDto;
import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.dto.salida.reserva.ReservaSalidaDto;
import com.example.demo.entity.Categoria;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("productos")
public class ProductoController {
    @Autowired
    public IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Listado de todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de productos obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<List<ProductoSalidaDto>> listarProductos() throws ResourceNotFoundException{
        return new ResponseEntity<>(productoService.listarProductos(), HttpStatus.OK);
    }
    //buscar producto con PathVariable
    @Operation(summary = "Búsqueda de un producto por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoSalidaDto> obtenerProductoPorId(@PathVariable Long id) throws ResourceNotFoundException{
        return new ResponseEntity<>(productoService.buscarProductoPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Busqueda de produtos segun categoria")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Id's inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Productos no encontrados",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/categorias")
    public ResponseEntity<List<ProductoSalidaDto>> obtenerProductoPorCategorias(@RequestBody  List<Categoria> categorias) {
        return new ResponseEntity<>(productoService.listarProductosPorCategorias(categorias), HttpStatus.OK);
    }

    @Operation(summary = "Registro de un nuevo Producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto guardado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<ProductoSalidaDto> guardar(@RequestBody @Valid ProductoEntradaDto producto) throws BadRequestException {
        return new ResponseEntity<>(productoService.registrarProducto(producto), HttpStatus.CREATED);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ProductoSalidaDto>> obtenerReservasDisponiblesPorParametros(@RequestBody DisponibilidadEntradaDto disponibilidadEntrada) throws ResourceNotFoundException {
        List<ProductoSalidaDto> productos = productoService.obtenerProductosDisponiblesPorParametros(disponibilidadEntrada);
        return ResponseEntity.ok(productos);
    }



    @Operation(summary = "Actualización de un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "UServer error",
                    content = @Content)
    })

    @PutMapping("/{id}")
    public ResponseEntity<ProductoSalidaDto> actualizarProducto(@PathVariable Long id, @RequestBody ProductoModificacionEntradaDto producto) throws ResourceNotFoundException{

        return new ResponseEntity<>(productoService.actualizarProducto(id, producto), HttpStatus.OK);
    }

    @Operation(summary = "Eliminación de un producto por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) throws ResourceNotFoundException {
        productoService.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/nombres")
    public ResponseEntity<List<ProductoSalidaDto>> buscarProductosPorNombre(@RequestParam String nombre) throws ResourceNotFoundException {
        List<ProductoSalidaDto> productos = productoService.buscarProductosPorNombre(nombre);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}
