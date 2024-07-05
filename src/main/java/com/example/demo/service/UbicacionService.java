package com.example.demo.service;

import com.example.demo.dto.entrada.ubicacion.UbicacionEntradaDto;
import com.example.demo.dto.salida.ubicacion.UbicacionSalidaDto;
import com.example.demo.entity.Ubicacion;
import com.example.demo.repository.UbicacionRepository;
import com.example.demo.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UbicacionService implements IUbicacionService {
    private final Logger LOGGER = LoggerFactory.getLogger(UbicacionService.class);
    private UbicacionRepository ubicacionRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UbicacionService(UbicacionRepository ubicacionRepository, ModelMapper modelMapper) {
        this.ubicacionRepository = ubicacionRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public UbicacionSalidaDto registrarUbicacion(UbicacionEntradaDto ubicacion) {

        LOGGER.info("UbicacionEntradaDto: " + JsonPrinter.toString(ubicacion));
        Ubicacion ubicacionEntidad = modelMapper.map(ubicacion, Ubicacion.class);

        //mandamos a persistir a la capa dao y obtenemos una entidad
        Ubicacion ubicacionAPersistir = ubicacionRepository.save(ubicacionEntidad);
        //transformamos la entidad obtenida en salidaDto
        UbicacionSalidaDto ubicacionSalidaDto = modelMapper.map(ubicacionAPersistir, UbicacionSalidaDto.class);
        LOGGER.info("UbicacionSalidaDto: " + JsonPrinter.toString(ubicacionSalidaDto));
        return ubicacionSalidaDto;

    }

    private void configureMapping() {
        modelMapper.typeMap(UbicacionEntradaDto.class, Ubicacion.class);

        modelMapper.typeMap(Ubicacion.class, UbicacionSalidaDto.class);

    }
}
