package com.videojuegos.service;

import com.videojuegos.entities.Foro;
import com.videojuegos.repository.ForoRepository;
import org.videojuegos.stereotype.Service;

import java.util.List;

@Service
public class ForoService {
    private final ForoRepository foroRepository;

    public ForoService(ForoRepository foroRepository) {
        this.foroRepository = foroRepository;
    }

    public List<Foro> listar() {
        return foroRepository.findAll();
    }

    public Foro buscarPorId(Long id) {
        return foroRepository.findById(id).orElse(null);
    }

    public Foro guardar(Foro foro) {
        return foroRepository.save(foro);
    }

    public void eliminar(Long id) {
        foroRepository.deleteById(id);
    }
}
