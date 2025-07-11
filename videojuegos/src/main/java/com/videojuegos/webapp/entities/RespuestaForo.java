/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegos.webapp.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RespuestaForo")
public class RespuestaForo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRespuesta;
    
    @ManyToOne
    @JoinColumn(name = "IdPost")
    private Foro post;
    
    @ManyToOne
    @JoinColumn(name = "IdUsuario")
    private Usuario usuario;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;
    
    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
}