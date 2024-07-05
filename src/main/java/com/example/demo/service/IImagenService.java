package com.example.demo.service;

import com.example.demo.dto.entrada.imagen.ImagenEntradaDto;
import com.example.demo.dto.salida.imagen.ImagenSalidaDto;
import com.example.demo.dto.salida.producto.ProductoSalidaDto;

import java.util.List;

public interface IImagenService {
    ImagenSalidaDto registrarImagen(ImagenEntradaDto imagen);

    List<ImagenSalidaDto> listarImagenes();

    void eliminarImagenByID(Long id);
}
