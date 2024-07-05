package com.example.demo.service;

import com.example.demo.dto.entrada.caracteristica.CaracteristicaEntradaDto;
import com.example.demo.dto.modificacion.caracteristica.CaracteristicaModificacionEntradaDto;
import com.example.demo.dto.salida.caracteristica.CaracteristicaSalidaDto;
import com.example.demo.entity.Caracteristica;
import com.example.demo.entity.Producto;
import com.example.demo.repository.CaracteristicaRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CaracteristicaService implements  ICaracteristicaService{
    private final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private CaracteristicaRepository caracteristicaRepository;
    private ProductoRepository productoRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CaracteristicaService(CaracteristicaRepository caracteristicaRepository, ProductoRepository productoRepository, ModelMapper modelMapper) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }
    @Override
    public CaracteristicaSalidaDto registrarCaracteristica(CaracteristicaEntradaDto caracteristica) {
        // Verificar si ya existe un alojamiento con el mismo nombre
        String nombreDelaCaracteristica= caracteristica.getNombre();
        if (caracteristicaRepository.findByNombre(nombreDelaCaracteristica).isPresent()) {
            throw new IllegalArgumentException("La caracteristica con nombre '" + nombreDelaCaracteristica + "' ya existe");
        }
        LOGGER.info("CaracteristicaEntradaDto: " + JsonPrinter.toString(caracteristica));
        Caracteristica caracteristicaEntidad = modelMapper.map(caracteristica, Caracteristica.class);

        //mandamos a persistir a la capa dao y obtenemos una entidad
        Caracteristica caracteristicaAPersistir = caracteristicaRepository.save(caracteristicaEntidad);
        //transformamos la entidad obtenida en salidaDto
        CaracteristicaSalidaDto caracteristicaSalidaDto = modelMapper.map(caracteristicaAPersistir, CaracteristicaSalidaDto.class);
        LOGGER.info("CaracteristicaSaldidaDto: " + JsonPrinter.toString(caracteristicaSalidaDto));
        return caracteristicaSalidaDto;
    }

    @Override
    public Void eliminarCaracteristicaByID(Long id) {
        Caracteristica caracteristica = caracteristicaRepository.getById(id);
        if (!caracteristicaRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("La caracteristica con is '" + id + " no existe");
        }
        for(Producto prd : caracteristica.getProductos()){
            prd.getCategorias().remove(caracteristica);
            productoRepository.save(prd);
        }

        caracteristicaRepository.deleteById(id);
        LOGGER.info("Se elimino la caracteristica con el id: {id}");
        return null;
    }

    @Override
    public List<CaracteristicaSalidaDto> listarCaracteristicas() {
        List<CaracteristicaSalidaDto> caracteristicasSalida = caracteristicaRepository.findAll()
                .stream()
                .map(caracteristica -> modelMapper.map(caracteristica, CaracteristicaSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todas las caracteristicas: {}", JsonPrinter.toString(caracteristicasSalida));
        return caracteristicasSalida;
    }

    @Override
    public void actualizarCaracteristicaByID(Long id, CaracteristicaModificacionEntradaDto caracteristica) {

    }

    private void configureMapping() {
        modelMapper.typeMap(CaracteristicaEntradaDto.class, Caracteristica.class);

        modelMapper.typeMap(Caracteristica.class, CaracteristicaSalidaDto.class);

    }
}
