package com.example.demo.dto.modificacion.favorito;

import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.dto.salida.usuario.UsuarioSalidaDto;
import com.example.demo.entity.Producto;
import com.example.demo.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoritoModificacionEntradaDto {
    private Long id;
    private String nombre;
    private UsuarioSalidaDto usuario;
    private ProductoSalidaDto producto;
}
