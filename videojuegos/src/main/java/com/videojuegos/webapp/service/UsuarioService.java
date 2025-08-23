package com.videojuegos.service;

import com.videojuegos.entities.Usuario;
import com.videojuegos.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    public UsuarioService(UsuarioRepository repo) { this.repo = repo; }

    public Optional<Usuario> findByCorreo(String correo){ return repo.findByCorreo(correo); }
    public Usuario save(Usuario u){ return repo.save(u); }
    public void deleteById(Long id){ repo.deleteById(id); }
}
