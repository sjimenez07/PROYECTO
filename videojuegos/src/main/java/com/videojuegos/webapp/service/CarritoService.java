/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegos.service;

import com.videojuegos.entities.CarritoItem;
import com.videojuegos.entities.Juego;
import com.videojuegos.entities.Usuario;
import com.videojuegos.repository.CarritoItemRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {
    private final CarritoItemRepository repo;
    public CarritoService(CarritoItemRepository repo){ this.repo = repo; }

    public List<CarritoItem> getCarrito(Usuario u){ return repo.findByUsuario(u); }

    public void addToCarrito(Usuario u, Juego j, int cantidad){
        List<CarritoItem> items = repo.findByUsuario(u);
        Optional<CarritoItem> existente = items.stream().filter(ci -> ci.getJuego().equals(j)).findFirst();
        if(existente.isPresent()){
            CarritoItem ci = existente.get();
            ci.setCantidad(ci.getCantidad() + cantidad);
            repo.save(ci);
        } else {
            CarritoItem ci = new CarritoItem();
            ci.setUsuario(u);
            ci.setJuego(j);
            ci.setCantidad(cantidad);
            repo.save(ci);
        }
    }

    public void removeFromCarrito(Usuario u, Juego j){
        repo.deleteByUsuarioAndJuego(u,j);
    }

    public void clear(Usuario u){
        repo.findByUsuario(u).forEach(repo::delete);
    }
}
