package com.videojuegos.service;

import com.videojuegos.entities.Juego;
import com.videojuegos.repository.JuegoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JuegoService {
    private final JuegoRepository repo;
    public JuegoService(JuegoRepository repo){ this.repo = repo; }
    public List<Juego> findAll(){ return repo.findAll(); }
    public Optional<Juego> findById(Long id){ return repo.findById(id); }
    public Juego save(Juego j){ return repo.save(j); }
    public void deleteById(Long id){ repo.deleteById(id); }
    public List<Juego> search(String q){ return repo.findByTituloContainingIgnoreCase(q); }
}
