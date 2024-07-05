package com.example.demo.service;

import com.example.demo.dto.entrada.caracteristica.CaracteristicaEntradaDto;
import com.example.demo.dto.entrada.categoria.CategoriaEntradaDto;
import com.example.demo.dto.entrada.disponibilidad.DisponibilidadEntradaDto;
import com.example.demo.dto.entrada.imagen.ImagenEntradaDto;
import com.example.demo.dto.entrada.producto.ProductoEntradaDto;
import com.example.demo.dto.modificacion.producto.ProductoModificacionEntradaDto;
import com.example.demo.dto.salida.caracteristica.CaracteristicaSalidaDto;
import com.example.demo.dto.salida.categoria.CategoriaSalidaDto;
import com.example.demo.dto.salida.imagen.ImagenSalidaDto;
import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.entity.*;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.FavoritoRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.ReservaRepository;
import com.example.demo.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductoService implements IProductoService{
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);
    private ProductoRepository productoRepository;

    private ReservaRepository reservaRepository;

    private FavoritoRepository favoritoRepository;
    private ModelMapper modelMapper;

    private ImagenService imagenService;

    private CaracteristicaService caracteristicaService;
    private CategoriaService categoriaService;



    @Autowired
    public ProductoService(ProductoRepository productoRepository, ReservaRepository reservaRepository, FavoritoRepository favoritoRepository, ModelMapper modelMapper, ImagenService imagenService, CaracteristicaService caracteristicaService, CategoriaService categoriaService) {
        this.productoRepository = productoRepository;
        this.reservaRepository = reservaRepository;
        this.favoritoRepository = favoritoRepository;
        this.modelMapper = modelMapper;
        this.caracteristicaService = caracteristicaService;
        this.categoriaService = categoriaService;
        configureMapping();
        this.imagenService = imagenService;
    }


    @Override
    public ProductoSalidaDto registrarProducto(ProductoEntradaDto producto) throws BadRequestException {
        LOGGER.info("ProductoEntradaDto: " + JsonPrinter.toString(producto));
        //chek existencia categorias/caracteristicas, y si no existen se crean
        Producto productoEntidad = modelMapper.map(producto, Producto.class);
        boolean productoExiste = chequearExistencia(productoEntidad);
        if(productoExiste == true){
            LOGGER.error("Ya existe un producto con ese nombre{}", producto.getNombre());
            BadRequestException exception = new BadRequestException("Ya existe un producto con ese nombre ${producto.getNombre()}");
            throw exception;
        }
        else{
            //mandamos a persistir a la capa dao y obtenemos una entidad
            Producto productoAPersistir = productoRepository.save(productoEntidad);
           List<ImagenSalidaDto> imagenesSalida = new ArrayList<ImagenSalidaDto>();
            //transformamos la entidad obtenida en salidaDto
            ProductoSalidaDto productoSalidaDto = modelMapper.map(productoAPersistir, ProductoSalidaDto.class);
            LOGGER.info("ProductoSalidaDto: " + JsonPrinter.toString(productoSalidaDto));
            return productoSalidaDto;
        }
    }


    @Override
    public List<ProductoSalidaDto> listarProductos() throws ResourceNotFoundException{
        List<ProductoSalidaDto> productoSalidaDtos = productoRepository.findAll()
                .stream()
                .map(producto -> modelMapper.map(producto, ProductoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los productos: {}", JsonPrinter.toString(productoSalidaDtos));
        if (productoSalidaDtos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos.");
        }
        return productoSalidaDtos;
    }

    @Override
    public ProductoSalidaDto buscarProductoPorId(Long id)throws ResourceNotFoundException {
        Producto productoBuscado = productoRepository.findById(id).orElse(null);
        ProductoSalidaDto productoEncontrado = null;

        LOGGER.debug("Producto buscado con ID {}: {}", id, productoBuscado);
        if (productoBuscado != null) {
            productoEncontrado = modelMapper.map(productoBuscado, ProductoSalidaDto.class);
            LOGGER.info("Producto encontrado: {}", JsonPrinter.toString(productoEncontrado));
        } else {
            LOGGER.error("El id no se encuentra registrado en la base de datos");
            throw new ResourceNotFoundException("No se ha encontrado el producto con id " + id);
        }

        return productoEncontrado;
    }

    @Override
    public void eliminarProducto(Long id) throws ResourceNotFoundException {
        Producto producto = productoRepository.getById(id);
        if (buscarProductoPorId(id) != null) {
            for(Imagen img : producto.getImagenes()){
                imagenService.eliminarImagenByID(img.getId());
            }
            for(Favorito fav : favoritoRepository.findAll()){
                if(fav.getProducto() == producto){
                    favoritoRepository.deleteById(fav.getId());
                }
            }
            productoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el producto con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el producto con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el paciente con id " + id);
        }
    }

    @Override
    public ProductoSalidaDto actualizarProducto( Long id, ProductoModificacionEntradaDto producto)throws ResourceNotFoundException  {
        LOGGER.info(id.toString());
        Producto productoRecibido = modelMapper.map(producto, Producto.class);
        Producto productoAActualizar = productoRepository.findById(id).orElse(null);

        ProductoSalidaDto productoSalidaDto = null;

        if (productoAActualizar != null) {
            productoRecibido.setUbicacion(productoAActualizar.getUbicacion());
            productoAActualizar = productoRecibido;
            productoRepository.save(productoAActualizar);
            productoSalidaDto = modelMapper.map(productoAActualizar, ProductoSalidaDto.class);
            LOGGER.warn("Producto actualizado: {}", JsonPrinter.toString(productoSalidaDto));
        } else {
            LOGGER.error("No fue posible actualizar el producto porque no se encuentra en nuestra base de datos");
        }


        return productoSalidaDto;
    }

    @Override
    public List<ProductoSalidaDto> listarProductosPorCategorias(List<Categoria> categorias) {
        List<ProductoSalidaDto> productoSalidaDtos = new ArrayList<>();
        List<ProductoSalidaDto> productosAux = new ArrayList<>();
        for(Categoria cat : categorias){
            productosAux = productoRepository.findByCategoryID(cat.getId().toString())
                    .stream()
                    .map(producto -> modelMapper.map(producto, ProductoSalidaDto.class))
                    .toList();
        }
        for(ProductoSalidaDto prdAux : productosAux){
            if (productoSalidaDtos.indexOf(prdAux) == -1) productoSalidaDtos.add(prdAux);
        }
        LOGGER.info("Listado de todos los productos: {}", JsonPrinter.toString(productoSalidaDtos));
        return productoSalidaDtos;
    }

    @Override
    public List<ProductoSalidaDto> buscarProductosPorNombre(String nombre) throws ResourceNotFoundException {
        List<ProductoSalidaDto> productoSalidaDtos = productoRepository.findByNombreContaining(nombre)
                .stream()
                .map(producto -> modelMapper.map(producto, ProductoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los productos que coinciden con el nombre enviado: {}", JsonPrinter.toString(productoSalidaDtos));
        return productoSalidaDtos;
    }
    @Override
    public List<ProductoSalidaDto> obtenerProductosDisponiblesPorParametros(DisponibilidadEntradaDto disponibilidadEntrada) throws ResourceNotFoundException {
        List<ProductoSalidaDto> productosSalida = listarProductos();
        List<ProductoSalidaDto> productosDisponibles = new ArrayList<>();
        Boolean tienePais = disponibilidadEntrada.getPais()!=null;
        LOGGER.info(String.valueOf(productosSalida.size()));
        LOGGER.info(JsonPrinter.toString(disponibilidadEntrada));
        for(ProductoSalidaDto  producto : productosSalida){
            LocalDate prdDesde = convertToLocalDate(producto.getDisponibilidad_Desde());
            LocalDate prdHasta = convertToLocalDate(producto.getDisponibilidad_Hasta());
            //producto dentro de fehcas de disponibilidad deseada
            if (tienePais){
                if(producto.getUbicacion().getPais().equals(disponibilidadEntrada.getPais()) ){
                    //chequear disponibilidad del producto
                    if(verificarDisponibilidad(producto, disponibilidadEntrada)){
                        productosDisponibles.add(producto);
                    }
                }
            }
            else{
                if(disponibilidadEntrada.getFechaInicio().isBefore(prdDesde)){
                    //chequear disponibilidad del producto
                    if(verificarDisponibilidad(producto, disponibilidadEntrada)){
                        productosDisponibles.add(producto);
                    }
                }
            }



            LOGGER.info(String.valueOf(productosDisponibles.size()));
        }
        return productosDisponibles;
    }

    public boolean verificarDisponibilidad(ProductoSalidaDto producto, DisponibilidadEntradaDto disponibilidadEntradaDto){
        Boolean disponible = true;
        LocalDate disponibilidadDesde = convertToLocalDate(producto.getDisponibilidad_Desde());
        LocalDate disponibilidadHasta = convertToLocalDate(producto.getDisponibilidad_Hasta());
        List<Reserva> reservas = reservaRepository.findByProductoIdAndFechaFinAfterAndFechaInicioBefore(
                producto.getId(), disponibilidadEntradaDto.getFechaInicio(), disponibilidadEntradaDto.getFechaFin());
        if(disponibilidadDesde.isAfter(disponibilidadEntradaDto.getFechaInicio())){
            disponible = false;
        }
        LOGGER.info(disponible.toString());
        if(disponibilidadHasta.isBefore(disponibilidadEntradaDto.getFechaFin())){
            disponible = false;
        }
        LOGGER.info(disponible.toString());
        if(!reservas.isEmpty()){
            disponible = false;
        }
        LOGGER.info(disponible.toString());
        return disponible;
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    private void configureMapping() {
        //PRODUCTOS
        modelMapper.typeMap(ProductoEntradaDto.class, Producto.class);

        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class);

        modelMapper.typeMap(Imagen.class, ImagenEntradaDto.class);

        //CARACTERISTICAS
        modelMapper.typeMap(Caracteristica.class, CaracteristicaEntradaDto.class);

        modelMapper.typeMap(CaracteristicaSalidaDto.class, Caracteristica.class);

        //CATEGORIAS
        modelMapper.typeMap(Categoria.class, CategoriaEntradaDto.class);

        modelMapper.typeMap(CategoriaSalidaDto.class, Categoria.class);
    }

    private boolean chequearExistencia(Producto productoEntidad) {
        boolean flag = false;
        List<Producto> productosPersistidos = productoRepository.findAll();
        for(Producto prod : productosPersistidos){
            LOGGER.info(prod.getNombre() +productoEntidad.getNombre());
            if((prod.getNombre()).equals( productoEntidad.getNombre())) {flag=true;}
        }
        return flag;
    }


}
