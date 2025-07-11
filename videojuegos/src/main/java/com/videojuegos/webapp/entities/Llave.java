/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegos.webapp.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Llave")
public class Llave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLlave;
    
    @Column(nullable = false, unique = true, length = 100)
    private String codigo;
    
    @ManyToOne
    @JoinColumn(name = "IdJuego")
    private Juego juego;
    
    @Column(nullable = false)
    private boolean disponible = true;
}
