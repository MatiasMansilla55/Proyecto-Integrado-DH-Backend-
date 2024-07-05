package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableSpringHttpSession
public class SecurityConfiguration {
    @Autowired
    AppUserDetailService userDetailService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry ->{

                    registry.requestMatchers("/auth/login","/home", "/usuarios/registrar/**","/login","/logout","/productos/","productos/nombres").permitAll();//pagina del home inicial y el login, registro de usuarios
                    //registry.requestMatchers("/usuarios/admin/**","admin/home/**","/productos/registrar","/productos/**","/usuarios/listar","/favoritos/**").hasRole("ADMIN");//toda url que tenga admin sera permitido solo para roles admin
                    registry.requestMatchers("/**").hasRole("ADMIN");//toda url sera permitido solo para roles admin
                    registry.requestMatchers("/categorias/listar",
                            "/caracteristicas/listar",
                            "/favoritos/mis-favortios",
                            "/favoritos/registrar",
                            "/favoritos/{id}",
                            "/imagenes/listar",
                            "/productos/listar",
                            "/productos/{id}",
                            "/productos/categorias",
                            "/productos/disponibles",
                            "/reservas/registrar",
                            "/reservas/listar",
                            "/reservar/mis-reservas",
                            "/usuarios/registrar",
                            "/usuarios/home"
                    ).hasRole("USER");
                    registry.anyRequest().authenticated();
                })
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                //.formLogin(httpSecurityFormLoginConfigurer -> {
                    //httpSecurityFormLoginConfigurer
                            //.loginPage("/login")
                            //.successHandler(new AuthenticationSuccessHandler())//esto es para redireccionar al usuario despues de login exitoso.
                            //.permitAll();
                //})
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer
                            .logoutUrl("/logout")
                            .logoutSuccessHandler(logoutSuccessHandler())  // Manejar el éxito del logout
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                            .permitAll();
                })
                .httpBasic(withDefaults())
                .build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return userDetailService;
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService((userDetailService));
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();  // Retorna un HTTP 200 OK en caso de logout exitoso
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    // Define el bean MapSessionRepository para el almacenamiento en memoria
    @Bean
    public MapSessionRepository sessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }
}
