package com.example.demo.service;

import com.example.demo.dto.entrada.disponibilidad.DisponibilidadEntradaDto;
import com.example.demo.dto.entrada.reserva.ReservaEntradaDto;
import com.example.demo.dto.salida.favorito.FavoritoSalidaDto;
import com.example.demo.dto.salida.imagen.ImagenSalidaDto;
import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.dto.salida.reserva.ReservaSalidaDto;
import com.example.demo.dto.salida.usuario.UsuarioSalidaDto;
import com.example.demo.entity.Favorito;
import com.example.demo.entity.Producto;
import com.example.demo.entity.Reserva;
import com.example.demo.entity.Usuario;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.ReservaRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService implements IReservaService {
    private final Logger LOGGER = LoggerFactory.getLogger(ReservaService.class);

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImagenService imagenService;
    @Autowired
    private EmailService emailService;


    public ReservaService(ReservaRepository reservaRepository, UsuarioService usuarioService, ProductoService productoService, ModelMapper modelMapper, ImagenService imagenService,EmailService emailService) {
        this.reservaRepository = reservaRepository;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.modelMapper = modelMapper;
        this.imagenService = imagenService;
        this.emailService = emailService;
        configureMapping();
    }

    @Override
    public ReservaSalidaDto registrarReserva(ReservaEntradaDto reserva) throws BadRequestException, ResourceNotFoundException {

        // Verificar si el usuario con el ID dado existe en la base de datos
        UsuarioSalidaDto usuarioSalidaDto = usuarioService.buscarUsuarioPorId(reserva.getUsuarioId());
        if (usuarioSalidaDto == null || !usuarioService.checkUsuarioEnSesionById(reserva.getUsuarioId())) {
            LOGGER.error("No se encontró el usuario con ID o el ID no pertence al usuario en uso. ID: {}", reserva.getUsuarioId());
            throw new ResourceNotFoundException("No se encontró el usuario con ID o el ID no pertence al usuario en uso. ID: " + reserva.getUsuarioId());
        }

        // Verificar si el producto con el ID dado existe en la base de datos
        ProductoSalidaDto productoSalidaDto = productoService.buscarProductoPorId(reserva.getProductoId());
        if (productoSalidaDto == null) {
            LOGGER.error("No se encontró el producto con ID {}", reserva.getProductoId());
            throw new BadRequestException("No se encontró el producto con ID " + reserva.getProductoId());
        }

        // Verificar disponibilidad
        if (!verificarDisponibilidad(productoSalidaDto,reserva)) {
            throw new BadRequestException("Producto no disponible en las fechas seleccionadas / El producto ya está reservado en las fechas seleccionadas");
        }{
            double precioTotal = calcularPrecioTotal(reserva.getFechaInicio(), reserva.getFechaFin(), productoSalidaDto.getPrecioNoche());
            reserva.setPrecio_total(precioTotal);
            LOGGER.info("ReservaEntradaDto: " + JsonPrinter.toString(reserva));

            // Crear la entidad Reserva y asignar usuario y producto
            Reserva reservaEntidad = new Reserva();
            reservaEntidad.setUsuario(modelMapper.map(usuarioSalidaDto, Usuario.class));
            reservaEntidad.setProducto(modelMapper.map(productoSalidaDto, Producto.class));
            reservaEntidad.setFechaInicio(reserva.getFechaInicio());
            reservaEntidad.setFechaFin(reserva.getFechaFin());
            reservaEntidad.setEstado(reserva.getEstado());
            reservaEntidad.setPrecio_total(precioTotal);

            // Guardar el reserva en la base de datos
            Reserva reservaGuardado = reservaRepository.save(reservaEntidad);

            // Mapear la entidad Reserva a un DTO de salida
            ReservaSalidaDto reservaSalidaDto = modelMapper.map(reservaGuardado, ReservaSalidaDto.class);

            // Enviar correo electrónico
            enviarCorreoReserva(reservaSalidaDto);


            LOGGER.info("Reserva registrada correctamente: {}", JsonPrinter.toString(reservaSalidaDto));

            return reservaSalidaDto;
        }

    }
    @Override
    public List<ReservaSalidaDto> listarReservas() {

        List<ReservaSalidaDto> reservaSalidaDtos = reservaRepository.findAll()
                .stream()
                .map(reserva -> modelMapper.map(reserva, ReservaSalidaDto.class))
                .toList();
        //if (reservaSalidaDtos.isEmpty()) {
        //throw new ResourceNotFoundException("No se encontraron reservas.");
        //}
        LOGGER.info("Listado de reservas: {}", JsonPrinter.toString(reservaSalidaDtos));
        return reservaSalidaDtos;
    }
    @Override
    public List<ReservaSalidaDto> obtenerReservasPorUsuario() {
    String usernameInSession = usuarioService.usuarioEnSession().getEmail();
        List<Reserva> reservas = reservaRepository.findByUsuarioEmail(usernameInSession);
        return reservas.stream()
                .map(reserva -> modelMapper.map(reserva, ReservaSalidaDto.class))
                .collect(Collectors.toList());
    }



    public double calcularPrecioTotal(LocalDate fechaInicio, LocalDate fechaFin, double precioNoche) {
        long dias = Duration.between(fechaInicio.atStartOfDay(), fechaFin.atStartOfDay()).toDays();
        return dias * precioNoche;
    }
    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public boolean verificarDisponibilidad(ProductoSalidaDto producto, ReservaEntradaDto reserva){

        // Verificar disponibilidad
        LocalDate disponibilidadDesde = convertToLocalDate(producto.getDisponibilidad_Desde());
        LocalDate disponibilidadHasta = convertToLocalDate(producto.getDisponibilidad_Hasta());

        List<Reserva> reservas = reservaRepository.findByProductoIdAndFechaFinAfterAndFechaInicioBefore(
                producto.getId(), reserva.getFechaInicio(), reserva.getFechaFin());
        Boolean noDisponible = (reserva.getFechaInicio().isBefore(disponibilidadDesde) || reserva.getFechaFin().isAfter(disponibilidadHasta) || !reservas.isEmpty());
        return !noDisponible;
    }



    //funcion para armar la estructura del eamil
    private void enviarCorreoReserva(ReservaSalidaDto reservaSalidaDto) {
        String destinatario = reservaSalidaDto.getUsuarioSalidaDto().getEmail();
        String asunto = "Detalles de su Reserva";
        String texto = "Estimado " + reservaSalidaDto.getUsuarioSalidaDto().getNombre() + ",\n\n" +
                "Gracias por su reserva. Aquí están los detalles de su reserva:\n\n" +
                "Producto: " + reservaSalidaDto.getProductoSalidaDto().getNombre() + "\n" +
                "Fecha de Inicio: " + reservaSalidaDto.getFechaInicio() + "\n" +
                "Fecha de Fin: " + reservaSalidaDto.getFechaFin() + "\n" +
                "Estado: " + reservaSalidaDto.getEstado() + "\n" +
                "Precio Total: $" + reservaSalidaDto.getPrecio_total() + "\n\n" +
                "Gracias por confiar en nosotros.\n" +
                "Saludos cordiales,\n" +
                "El equipo de reservas";

        emailService.sendSimpleMessage(destinatario, asunto, texto);
    }

    private void configureMapping() {
        modelMapper.typeMap(UsuarioSalidaDto.class, Usuario.class);
        modelMapper.typeMap(ProductoSalidaDto.class, Producto.class);
        modelMapper.typeMap(Reserva.class, ReservaSalidaDto.class)
                .addMappings(mapper -> mapper.map(Reserva::getUsuario, ReservaSalidaDto::setUsuarioSalidaDto));
        modelMapper.typeMap(Reserva.class, ReservaSalidaDto.class)
                .addMappings(mapper -> mapper.map(Reserva::getProducto, ReservaSalidaDto::setProductoSalidaDto));

        modelMapper.typeMap(DisponibilidadEntradaDto.class, Reserva.class);

        //modelMapper.typeMap(Imagen.class, ImagenEntradaDto.class);
    }
}
