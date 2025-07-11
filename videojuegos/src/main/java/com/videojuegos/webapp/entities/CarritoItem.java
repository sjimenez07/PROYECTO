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
@Table(name = "CarritoItem")
public class CarritoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarritoItem;
    
    @ManyToOne
    @JoinColumn(name = "IdCarrito")
    private Carrito carrito;
    
    @ManyToOne
    @JoinColumn(name = "IdJuego")
    private Juego juego;
    
    @Column(nullable = false)
    private int cantidad = 1;
}
