/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegos.webapp.repository;

import com.videojuegos.webapp.entities.CarritoItem;
import com.videojuegos.webapp.entities.Carrito;
import com.videojuegos.webapp.entities.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
    List<CarritoItem> findByUsuario(Usuario usuario);
    void deleteByUsuarioAndJuego(Usuario usuario, Juego juego);
}
