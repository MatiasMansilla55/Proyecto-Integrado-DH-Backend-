package com.example.demo.service;

import com.example.demo.dto.entrada.imagen.ImagenEntradaDto;
import com.example.demo.dto.modificacion.imagen.ImagenModificacionEntradaDto;
import com.example.demo.dto.salida.imagen.ImagenSalidaDto;
import com.example.demo.entity.Imagen;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.ImagenRepository;
import com.example.demo.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenService implements IImagenService {
    private final Logger LOGGER = LoggerFactory.getLogger(ImagenService.class);
    private ImagenRepository imagenRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ImagenService(ImagenRepository imagenRepository, ModelMapper modelMapper) {
        this.imagenRepository = imagenRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public ImagenSalidaDto registrarImagen(ImagenEntradaDto imagen) {
        // Verificar si ya existe un alojamiento con el mismo nombre
        String nombreDelaImagen = imagen.getNombre();
        if (imagenRepository.findByNombre(nombreDelaImagen).isPresent()) {
            throw new IllegalArgumentException("La categoria con nombre '" + nombreDelaImagen + "' ya existe");
        }
        LOGGER.info("imagenEntradaDto: " + JsonPrinter.toString(imagen));
        Imagen imagenEntidad = modelMapper.map(imagen, Imagen.class);
        LOGGER.info(imagenEntidad.getRutaDeArchivo());
        //mandamos a persistir a la capa dao y obtenemos una entidad
        Imagen imagenesAPersistir = imagenRepository.save(imagenEntidad);
        //transformamos la entidad obtenida en salidaDto
        ImagenSalidaDto imagenSalidaDto = modelMapper.map(imagenesAPersistir, ImagenSalidaDto.class);
        LOGGER.info("ImagenesSalidaDto: " + JsonPrinter.toString(imagenSalidaDto));
        return imagenSalidaDto;

    }

    public ImagenSalidaDto actualizarImagen(ImagenModificacionEntradaDto imagen){
        Imagen imagenRecibida = modelMapper.map(imagen, Imagen.class);
        Imagen imagenAActualizar = imagenRepository.findById(imagen.getId()).orElse(null);

        if (imagenAActualizar != null){
            imagenRepository.save(imagenRecibida);
            ImagenSalidaDto imagenActualizada = modelMapper.map(imagenRecibida, ImagenSalidaDto.class);
            LOGGER.info("Imagen {imagen.getId()} actualizada: ${imagenActualizada}");
            return imagenActualizada;
        }
        return null;
    }

    @Override
    public List<ImagenSalidaDto> listarImagenes() {
        List<ImagenSalidaDto> imagenSalidaDtos = imagenRepository.findAll()
                .stream()
                .map(imagen -> modelMapper.map(imagen, ImagenSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos las las imagaenes: {}", JsonPrinter.toString(imagenSalidaDtos));
        return imagenSalidaDtos;
    }

    @Override
    public void eliminarImagenByID(Long id) {

    }


    private void configureMapping() {
        modelMapper.typeMap(ImagenEntradaDto.class, Imagen.class);

        modelMapper.typeMap(Imagen.class, ImagenSalidaDto.class);

    }
}
