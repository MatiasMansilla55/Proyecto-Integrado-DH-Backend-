package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.entrada.usuario.UsuarioEntradaDto;
import com.example.demo.dto.modificacion.usuario.UsuarioModificacionEntradaDto;
import com.example.demo.dto.salida.usuario.UsuarioSalidaDto;
import com.example.demo.entity.Usuario;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.utils.JsonPrinter;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private AuthenticationManager authenticationManager;
    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EmailService emailService;
    private ModelMapper modelMapper;

    @Autowired
    public UsuarioService(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository, EmailService emailService, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public UsuarioSalidaDto registrarUsuario(UsuarioEntradaDto usuario) {

        LOGGER.info("UsuarioEntradaDto: " + JsonPrinter.toString(usuario));
        Usuario usuarioEntidad = modelMapper.map(usuario, Usuario.class);

        //mandamos a persistir a la capa dao y obtenemos una entidad
        Usuario usuarioAPersistir = usuarioRepository.save(usuarioEntidad);
        //transformamos la entidad obtenida en salidaDto
        UsuarioSalidaDto usuarioSalidaDto = modelMapper.map(usuarioAPersistir, UsuarioSalidaDto.class);
        emailService.sendSimpleMessage(usuarioSalidaDto.getEmail(),"Su cuenta fue creada exitosamente","Bienvenido " + usuarioSalidaDto.getNombre() +", gracias por confiar en nosotros. ¡Ya puedes ingresar con tu usuario y encontrar tu próxima aventura!");
        LOGGER.info("UsuarioSalidaDto: " + JsonPrinter.toString(usuarioSalidaDto));
        return usuarioSalidaDto;

    }

    @Override
    public List<UsuarioSalidaDto> listarUsuarios() {
        List<UsuarioSalidaDto> usuarioSalidaDtos = usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los usuarios: {}", JsonPrinter.toString(usuarioSalidaDtos));
        return usuarioSalidaDtos;
    }

    @Override
    public UsuarioSalidaDto buscarUsuarioPorId(Long id) {
        Usuario usuarioBuscado = usuarioRepository.findById(id).orElse(null);
        UsuarioSalidaDto usuarioEncontrado = null;

        LOGGER.debug("Usuario buscado con ID {}: {}", id, usuarioBuscado);
        if (usuarioBuscado != null) {
            usuarioEncontrado = modelMapper.map(usuarioBuscado, UsuarioSalidaDto.class);
            LOGGER.info("Usuario encontrado: {}", JsonPrinter.toString(usuarioEncontrado));
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return usuarioEncontrado;
    }


    @Override
    public void eliminarUsuario(Long id) throws ResourceNotFoundException {
        if (buscarUsuarioPorId(id) != null) {

            usuarioRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el usuario con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el usuario con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el usuario con id " + id);
        }
    }

    @Override
    public UsuarioSalidaDto actualizarUsuario( UsuarioModificacionEntradaDto usuario, Long id) {

        Usuario usuarioRecibido = modelMapper.map(usuario, Usuario.class);
        Usuario usuarioAActualizar = usuarioRepository.findById(usuarioRecibido.getId()).orElse(null);

        UsuarioSalidaDto usuarioSalidaDto = null;

        if (usuarioAActualizar != null) {
            usuarioAActualizar = usuarioRecibido;
            usuarioRepository.save(usuarioAActualizar);
            usuarioSalidaDto = modelMapper.map(usuarioAActualizar, UsuarioSalidaDto.class);
            LOGGER.warn("Usuario actualizado: {}", JsonPrinter.toString(usuarioSalidaDto));

        } else {
            LOGGER.error("No fue posible actualizar el usuario porque no se encuentra en nuestra base de datos");
            //lanzar excepcion correspondiente
        }


        return usuarioSalidaDto;
    }

    @Override
    public UsuarioSalidaDto login (LoginRequest loginRequest, HttpSession session){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getContrasenia()));
            //Estoy guardando la autenticacion en el SecurityContextHolder para que este disponible en el resto de la aplicacion
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst().orElse("");

            Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail());
            UsuarioSalidaDto usuarioSalidaDto = modelMapper.map(usuario, UsuarioSalidaDto.class);
            return usuarioSalidaDto;
        } catch (AuthenticationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UsuarioSalidaDto logout(HttpSession session) {
        session.invalidate();
        return null;
    }

    @Override
    public UsuarioSalidaDto usuarioEnSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername());
        return modelMapper.map(usuario, UsuarioSalidaDto.class);
    }



    public boolean checkUsuarioEnSesionById(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername());
        return usuario.getId().equals(id);
    }

    private boolean chequearExistencia(Usuario usuarioEntidad) {
        boolean flag = false;
        List<Usuario> usuariosPersistidos = usuarioRepository.findAll();
        for(Usuario usuario : usuariosPersistidos){
            LOGGER.info(usuario.getNombre() +usuarioEntidad.getNombre());
            if((usuario.getNombre()).equals( usuarioEntidad.getNombre())) {flag=true;}
        }
        return flag;
    }
    private void configureMapping() {
        modelMapper.typeMap(UsuarioEntradaDto.class, Usuario.class);

        modelMapper.typeMap(Usuario.class, UsuarioSalidaDto.class);


    }
}