package com.videojuegos.service;

import com.videojuegos.entities.Juego;
import com.videojuegos.entities.Llave;
import com.videojuegos.repository.LlaveRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LlaveService {
    private final LlaveRepository repo;
    public LlaveService(LlaveRepository repo){ this.repo = repo; }

    public List<Llave> disponiblesParaJuego(Juego j){
        return repo.findByJuegoAndVendidaFalse(j);
    }
    public Llave save(Llave l){ return repo.save(l); }
    public Optional<Llave> findById(Long id){ return repo.findById(id); }
}
