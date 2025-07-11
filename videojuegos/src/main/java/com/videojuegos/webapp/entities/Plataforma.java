/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegos.webapp.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Plataforma")
public class Plataforma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlataforma;
    
    @Column(nullable = false, length = 50)
    private String nombre;
    
    @Column(name = "UrlActivacion")
    private String urlActivacion;
    
    @OneToMany(mappedBy = "plataforma", cascade = CascadeType.ALL)
    private List<Juego> juegos;
}

