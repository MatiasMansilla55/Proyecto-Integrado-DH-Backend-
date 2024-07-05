package com.example.demo.dto.salida.favorito;

import com.example.demo.dto.salida.producto.ProductoSalidaDto;
import com.example.demo.dto.salida.usuario.UsuarioSalidaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoritoSalidaDto {
    private Long id;
    private String nombre;
    private UsuarioSalidaDto usuario;
    private ProductoSalidaDto producto;

    @Override
    public String toString() {
        return "FavoritoSalidaDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", usuarioSalidaDto=" + usuario +
                ", productoSalidaDto=" + producto +
                '}';
    }
}
