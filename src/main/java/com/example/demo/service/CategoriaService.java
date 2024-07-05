package com.example.demo.service;

import com.example.demo.dto.entrada.categoria.CategoriaEntradaDto;
import com.example.demo.dto.modificacion.categoria.CategoriaModificacionEntradaDto;
import com.example.demo.dto.modificacion.producto.ProductoModificacionEntradaDto;
import com.example.demo.dto.salida.categoria.CategoriaSalidaDto;
import com.example.demo.entity.Categoria;
import com.example.demo.entity.Producto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService implements ICategoriaService {
    private final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private CategoriaRepository categoriaRepository;
    private ProductoRepository productoRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository, ProductoRepository productoRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public CategoriaSalidaDto registrarCategoria(CategoriaEntradaDto categoria) {
        // Verificar si ya existe un alojamiento con el mismo nombre
        String nombreDelaCategoria = categoria.getNombre();
        if (categoriaRepository.findByNombre(nombreDelaCategoria).isPresent()) {
            throw new IllegalArgumentException("La categoria con nombre '" + nombreDelaCategoria + "' ya existe");
        }
        LOGGER.info("CategoriaEntradaDto: " + JsonPrinter.toString(categoria));
        Categoria categoriaEntidad = modelMapper.map(categoria, Categoria.class);

        //mandamos a persistir a la capa dao y obtenemos una entidad
        Categoria categoriaAPersistir = categoriaRepository.save(categoriaEntidad);
        //transformamos la entidad obtenida en salidaDto
        CategoriaSalidaDto categoriaSalidaDto = modelMapper.map(categoriaAPersistir, CategoriaSalidaDto.class);
        LOGGER.info("CategoriaSalidaDto: " + JsonPrinter.toString(categoriaSalidaDto));
        return categoriaSalidaDto;

    }

    @Override
    public Void eliminarCategoriaByID(Long id) throws ResourceNotFoundException {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);;
        if (categoria == null) {
            throw new IllegalArgumentException("La categoria con is '" + id + " no existe");
        }
        //
        for(Producto prd : categoria.getProductos()){
            prd.getCategorias().remove(categoria);
            productoRepository.save(prd);
        }
        categoriaRepository.deleteById(id);
        LOGGER.info("Se elimino la categoria con el id: {id}");
        return null;
    }

    @Override
    public List<CategoriaSalidaDto> listarCategorias() {
        List<CategoriaSalidaDto> categoriasSalidaDto = categoriaRepository.findAll()
                .stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todas las categorias: {}", JsonPrinter.toString(categoriasSalidaDto));
        return categoriasSalidaDto;
    }

    @Override
    public void actualizarCategoriaByID(Long id, CategoriaModificacionEntradaDto categoria) {

    }

    private void configureMapping() {
        modelMapper.typeMap(CategoriaEntradaDto.class, Categoria.class);

        modelMapper.typeMap(Categoria.class, CategoriaSalidaDto.class);

        modelMapper.typeMap(Producto.class, ProductoModificacionEntradaDto.class);

    }
}
