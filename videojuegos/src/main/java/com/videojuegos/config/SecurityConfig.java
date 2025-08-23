/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.videojuegos.config;


import com.videojuegos.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository repo) {
        return username -> {
            return repo.findByCorreo(username).map(u -> {
                return User.withUsername(u.getCorreo())
                        .password(u.getPassword())
                        .roles(u.getRol().replace("ROLE_", ""))
                        .build();
            }).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Recursos públicos
                .requestMatchers("/css/**","/js/**","/images/**","/","/login","/register").permitAll()
                .requestMatchers("/juegos/**", "/admin/juegos/imagen/**").permitAll()
                // Foro - lectura pública, escritura autenticada
                .requestMatchers("/foro", "/foro/{id}").permitAll()
                .requestMatchers("/foro/crear", "/foro/{id}/responder").authenticated()
                // Admin
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Carrito requiere autenticación
                .requestMatchers("/carrito/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(log -> log
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}