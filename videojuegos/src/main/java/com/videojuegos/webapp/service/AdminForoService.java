package com.videojuegos.service;

import com.videojuegos.entities.Foro;
import com.videojuegos.repository.ForoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminForoService {
    private final ForoRepository repo;

    public AdminForoService(ForoRepository repo) {
        this.repo = repo;
    }

    public List<Foro> listar() {
        return repo.findAll();
    }

    public Optional<Foro> buscar(Long id) {
        return repo.findById(id);
    }

    public Foro guardar(Foro foro) {
        return repo.save(foro);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
