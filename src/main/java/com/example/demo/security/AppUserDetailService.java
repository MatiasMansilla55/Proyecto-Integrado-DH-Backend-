package com.example.demo.security;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AppUserDetailService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
       Usuario usuario = usuarioRepository.findByEmail(userEmail);
        if (usuario != null) {
            // Asumiendo que el rol está almacenado como una cadena en el campo `rol`
            //String role = "ROLE_" + usuarioObj.getRol().toUpperCase(); // Por ejemplo, "ROLE_ADMIN" o "ROLE_USER"
            return User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getContrasenia())
                    .roles(getRoles(usuario))
                    .build();
        } else {
            throw new UsernameNotFoundException(userEmail);
        }
    }
    private String[] getRoles(Usuario usuario){
        if(usuario.getRol()==null){
            return new String[]{"USER"};
        }
        return usuario.getRol().split(",");
    }
}

