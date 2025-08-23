package com.ProyectoTIENDA.repository;

import com.ProyectoTIENDA.entities.Llave;
import com.ProyectoTIENDA.entities.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LlaveRepository extends JpaRepository<Llave, Long> {
    List<Llave> findByJuegoAndVendidaFalse(Juego juego);
}
