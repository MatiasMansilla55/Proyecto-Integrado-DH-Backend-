package com.example.demo.service;


import com.example.demo.dto.entrada.disponibilidad.DisponibilidadEntradaDto;
import com.example.demo.dto.entrada.producto.ProductoEntradaDto;
import com.example.demo.dto.modificacion.producto.ProductoModificacionEntradaDto;
import com.example.demo.dto.salida.categoria.CategoriaSalidaDto;
import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.dto.salida.reserva.ReservaSalidaDto;
import com.example.demo.entity.Categoria;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Set;

public interface IProductoService {
    ProductoSalidaDto registrarProducto(ProductoEntradaDto producto) throws BadRequestException;

    List<ProductoSalidaDto> listarProductos()throws ResourceNotFoundException;

    ProductoSalidaDto buscarProductoPorId(Long id)throws ResourceNotFoundException;

    void eliminarProducto(Long id) throws ResourceNotFoundException;

    ProductoSalidaDto actualizarProducto(Long id,ProductoModificacionEntradaDto producto)throws ResourceNotFoundException;

    List<ProductoSalidaDto> listarProductosPorCategorias(List<Categoria> categorias);
    List<ProductoSalidaDto> buscarProductosPorNombre(String nombre) throws ResourceNotFoundException;
    List<ProductoSalidaDto> obtenerProductosDisponiblesPorParametros(DisponibilidadEntradaDto disponibilidadEntrada) throws ResourceNotFoundException;

}
