package com.videojuegos.service;

import com.videojuegos.entities.RespuestaForo;
import com.videojuegos.repository.RespuestaForoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminRespuestaForoService {
    private final RespuestaForoRepository repo;

    public AdminRespuestaForoService(RespuestaForoRepository repo) {
        this.repo = repo;
    }

    public List<RespuestaForo> listar() {
        return repo.findAll();
    }

    public Optional<RespuestaForo> buscar(Long id) {
        return repo.findById(id);
    }

    public RespuestaForo guardar(RespuestaForo respuesta) {
        return repo.save(respuesta);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
