/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegos.webapp.service;

import com.videojuegos.webapp.entities.*;
import com.videojuegos.webapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class CarritoService {
    
    @Autowired
    private CarritoRepository carritoRepository;
    
    @Autowired
    private CarritoItemRepository carritoItemRepository;
    
    public Carrito obtenerOCrearCarrito(Usuario usuario) {
        Optional<Carrito> carritoExistente = carritoRepository.findByUsuario(usuario);
        if (carritoExistente.isPresent()) {
            return carritoExistente.get();
        }
        
        Carrito nuevoCarrito = new Carrito();
        nuevoCarrito.setUsuario(usuario);
        return carritoRepository.save(nuevoCarrito);
    }
    
    @Transactional
    public void agregarJuego(Usuario usuario, Juego juego, int cantidad) {
        Carrito carrito = obtenerOCrearCarrito(usuario);
        
        Optional<CarritoItem> itemExistente = carritoItemRepository.findByCarritoAndJuego(carrito, juego);
        
        if (itemExistente.isPresent()) {
            CarritoItem item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
            carritoItemRepository.save(item);
        } else {
            CarritoItem nuevoItem = new CarritoItem();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setJuego(juego);
            nuevoItem.setCantidad(cantidad);
            carritoItemRepository.save(nuevoItem);
        }
    }
    
    @Transactional
    public void eliminarJuego(Usuario usuario, Juego juego) {
        Carrito carrito = obtenerOCrearCarrito(usuario);
        Optional<CarritoItem> item = carritoItemRepository.findByCarritoAndJuego(carrito, juego);
        item.ifPresent(carritoItemRepository::delete);
    }
    
    @Transactional
    public void limpiarCarrito(Usuario usuario) {
        Carrito carrito = obtenerOCrearCarrito(usuario);
        if (carrito.getItems() != null) {
            carrito.getItems().clear();
            carritoRepository.save(carrito);
        }
    }
}
