/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegos.webapp.repository;

import com.videojuegos.webapp.entities.Llave;
import com.videojuegos.webapp.entities.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LlaveRepository extends JpaRepository<Llave, Long> {
    List<Llave> findByJuegoAndDisponible(Juego juego, boolean disponible);
    Optional<Llave> findFirstByJuegoAndDisponibleTrue(Juego juego);
    long countByJuegoAndDisponible(Juego juego, boolean disponible);
}