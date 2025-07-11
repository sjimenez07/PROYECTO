/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegos.webapp.service;

import com.videojuegos.webapp.entities.Juego;
import com.videojuegos.webapp.entities.Plataforma;
import com.videojuegos.webapp.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class JuegoService {
    
    @Autowired
    private JuegoRepository juegoRepository;
    
    public List<Juego> findAll() {
        return juegoRepository.findAll();
    }
    
    public Optional<Juego> findById(Long id) {
        return juegoRepository.findById(id);
    }
    
    public Juego save(Juego juego) {
        return juegoRepository.save(juego);
    }
    
    public void deleteById(Long id) {
        juegoRepository.deleteById(id);
    }
    
    public List<Juego> findByPlataforma(Plataforma plataforma) {
        return juegoRepository.findByPlataforma(plataforma);
    }
    
    public List<Juego> buscarPorTitulo(String titulo) {
        return juegoRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    public List<Juego> findByPrecio(BigDecimal min, BigDecimal max) {
        return juegoRepository.findByPrecioBetween(min, max);
    }
    
    public List<Juego> findJuegosDisponibles() {
        return juegoRepository.findJuegosDisponibles();
    }
    
    public List<Juego> findJuegosRecientes() {
        return juegoRepository.findJuegosRecientes();
    }
}
