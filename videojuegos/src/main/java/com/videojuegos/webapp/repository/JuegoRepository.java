package com.ProyectoTIENDA.repository;

import com.ProyectoTIENDA.entities.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JuegoRepository extends JpaRepository<Juego, Long> {
    List<Juego> findByTituloContainingIgnoreCase(String q);
}
