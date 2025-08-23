package com.videojuegos.service;

import com.videojuegos.entities.RespuestaForo;
import com.videojuegos.repository.RespuestaForoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RespuestaForoService {
    private final RespuestaForoRepository respuestaForoRepository;
    
    public RespuestaForoService(RespuestaForoRepository respuestaForoRepository) {
        this.respuestaForoRepository = respuestaForoRepository;
    }
    
    @Transactional(readOnly = true)
    public List<RespuestaForo> listar() {
        return respuestaForoRepository.findAll();
    }
    
    public RespuestaForo guardar(RespuestaForo respuesta) {
        return respuestaForoRepository.save(respuesta);
    }
    
    public void eliminar(Long id) {
        respuestaForoRepository.deleteById(id);
    }
}