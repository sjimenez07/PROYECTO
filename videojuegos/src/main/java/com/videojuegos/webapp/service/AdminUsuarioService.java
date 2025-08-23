package com.videojuegos.service;

import com.videojuegos.entities.Usuario;
import com.videojuegos.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminUsuarioService {
    private final UsuarioRepository repo;

    public AdminUsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Optional<Usuario> buscar(Long id) {
        return repo.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        return repo.save(usuario);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
