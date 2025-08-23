package com.videojuegos.service;

import com.videojuegos.entities.Juego;
import com.videojuegos.entities.Llave;
import com.videojuegos.repository.JuegoRepository;
import com.videojuegos.repository.LlaveRepository;
import com.videojuegos.repository.PlataformaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminJuegoService {
    private final JuegoRepository repo;
    private final PlataformaRepository plataformaRepo;
    private final LlaveRepository llaveRepo;

    public AdminJuegoService(JuegoRepository repo, PlataformaRepository plataformaRepo, LlaveRepository llaveRepo) {
        this.repo = repo;
        this.plataformaRepo = plataformaRepo;
        this.llaveRepo = llaveRepo;
    }

    public List<Juego> listar() {
        return repo.findAll();
    }

    public Optional<Juego> buscar(Long id) {
        return repo.findById(id);
    }

    public Juego guardar(Juego juego) {
        boolean esNuevo = juego.getId() == null;
        Integer stockAnterior = 0;
        
        // Si es edición, obtener stock anterior
        if (!esNuevo) {
            Optional<Juego> juegoExistente = repo.findById(juego.getId());
            if (juegoExistente.isPresent()) {
                stockAnterior = juegoExistente.get().getStock();
            }
        }

        // Si no tiene plataforma asignada, asignar la primera disponible
        if (juego.getPlataforma() == null) {
            plataformaRepo.findAll().stream()
                    .findFirst()
                    .ifPresent(juego::setPlataforma);
        }

        // Guardar el juego primero
        Juego juegoGuardado = repo.save(juego);

        // Generar llaves si es necesario
        if (esNuevo) {
            // Juego nuevo: generar llaves según el stock
            generarLlaves(juegoGuardado, juego.getStock());
        } else {
            // Juego existente: generar llaves adicionales si aumentó el stock
            int stockNuevo = juego.getStock();
            if (stockNuevo > stockAnterior) {
                int llavesAGenerar = stockNuevo - stockAnterior;
                generarLlaves(juegoGuardado, llavesAGenerar);
            }
        }

        return juegoGuardado;
    }

    private void generarLlaves(Juego juego, int cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            Llave llave = new Llave();
            // Generar código único para la llave
            String codigo = generarCodigoLlave(juego, i);
            llave.setCodigo(codigo);
            llave.setJuego(juego);
            llave.setVendida(false);
            llaveRepo.save(llave);
        }
        System.out.println("Generadas " + cantidad + " llaves para el juego: " + juego.getTitulo());
    }

    private String generarCodigoLlave(Juego juego, int numeroLlave) {
        // Limpiar título para crear código
        String tituloLimpio = juego.getTitulo()
                .replaceAll("[^A-Za-z0-9]", "")
                .toUpperCase();
        
        // Limitar longitud del título
        if (tituloLimpio.length() > 10) {
            tituloLimpio = tituloLimpio.substring(0, 10);
        }
        
        // Generar timestamp para unicidad
        long timestamp = System.currentTimeMillis();
        String timestampStr = String.valueOf(timestamp).substring(8); // Últimos 5 dígitos
        
        return "KEY-" + tituloLimpio + "-" + timestampStr + "-" + String.format("%03d", numeroLlave);
    }

    public void eliminar(Long id) {
        // Al eliminar un juego, también eliminamos sus llaves asociadas
        Optional<Juego> juego = repo.findById(id);
        if (juego.isPresent()) {
            // Las llaves se eliminarán automáticamente por cascada si está configurado
            // O puedes eliminarlas manualmente aquí si es necesario
            repo.deleteById(id);
        }
    }
}