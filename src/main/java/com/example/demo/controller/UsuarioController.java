package com.example.demo.controller;

import com.example.demo.dto.entrada.producto.ProductoEntradaDto;
import com.example.demo.dto.entrada.usuario.UsuarioEntradaDto;
import com.example.demo.dto.modificacion.producto.ProductoModificacionEntradaDto;
import com.example.demo.dto.modificacion.usuario.UsuarioModificacionEntradaDto;
import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.dto.salida.usuario.UsuarioSalidaDto;
import com.example.demo.entity.Producto;
import com.example.demo.entity.Usuario;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.IProductoService;
import com.example.demo.service.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.swing.text.StyledEditorKit;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {


    public IUsuarioService usuarioService;
    public IProductoService productoService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioController(IUsuarioService usuarioService, IProductoService productoService) {
        this.usuarioService = usuarioService;
        this.productoService = productoService;

    }

    @Operation(summary = "Registro de una nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario guardado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/registrar")

    public ResponseEntity<UsuarioSalidaDto> guardar(@RequestBody @Valid UsuarioEntradaDto usuario){

        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        return new ResponseEntity<>(usuarioService.registrarUsuario(usuario), HttpStatus.CREATED);
    }



    @Operation(summary = "Eliminación de un usuario por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) throws ResourceNotFoundException {
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "Actualización de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "UServer error",
                    content = @Content)
    })

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioSalidaDto> actualizarUsuario(@RequestBody UsuarioModificacionEntradaDto usuario, @PathVariable Long id){
        return new ResponseEntity<>(usuarioService.actualizarUsuario(usuario, id),HttpStatus.OK);

    }
    @Operation(summary = "Listado de todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de usuarios obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })



    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioSalidaDto>> listarUsuarios(){

        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/home")
    public ResponseEntity<UsuarioSalidaDto> usuarioEnSession() {
        return new ResponseEntity<>(usuarioService.usuarioEnSession(), HttpStatus.OK);
    }


}
