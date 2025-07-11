/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegos.webapp.repository;

import com.videojuegos.webapp.entities.Opinion;
import com.videojuegos.webapp.entities.Juego;
import com.videojuegos.webapp.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {
    List<Opinion> findByJuegoOrderByFechaDesc(Juego juego);
    Optional<Opinion> findByUsuarioAndJuego(Usuario usuario, Juego juego);
    List<Opinion> findByUsuario(Usuario usuario);
}