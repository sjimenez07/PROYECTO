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
@Table(name = "PedidoItem")
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedidoItem;
    
    @ManyToOne
    @JoinColumn(name = "IdPedido")
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "IdJuego")
    private Juego juego;
    
    @ManyToOne
    @JoinColumn(name = "IdLlave")
    private Llave llave;
}