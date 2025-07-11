/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegos.webapp.repository;

import com.videojuegos.webapp.entities.Juego;
import com.videojuegos.webapp.entities.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {
    List<Juego> findByPlataforma(Plataforma plataforma);
    List<Juego> findByTituloContainingIgnoreCase(String titulo);
    List<Juego> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
    
    @Query("SELECT j FROM Juego j WHERE j.llaves IS NOT EMPTY AND EXISTS (SELECT l FROM Llave l WHERE l.juego = j AND l.disponible = true)")
    List<Juego> findJuegosDisponibles();
    
    @Query("SELECT j FROM Juego j ORDER BY j.fechaLanzamiento DESC")
    List<Juego> findJuegosRecientes();
}