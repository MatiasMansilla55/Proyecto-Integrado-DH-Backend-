package com.example.demo.service;

import com.example.demo.dto.entrada.categoria.CategoriaEntradaDto;
import com.example.demo.dto.modificacion.categoria.CategoriaModificacionEntradaDto;
import com.example.demo.dto.salida.categoria.CategoriaSalidaDto;
import com.example.demo.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ICategoriaService {
    CategoriaSalidaDto registrarCategoria(CategoriaEntradaDto categoria);

    Void eliminarCategoriaByID(Long id) throws ResourceNotFoundException;

    List<CategoriaSalidaDto> listarCategorias();

    void actualizarCategoriaByID(Long id, CategoriaModificacionEntradaDto categoria);

}
