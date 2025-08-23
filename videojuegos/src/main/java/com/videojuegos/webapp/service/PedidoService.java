package com.videojuegos.service;

import com.videojuegos.entities.*;
import com.videojuegos.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepo;
    private final LlaveRepository llaveRepo;
    public PedidoService(PedidoRepository pedidoRepo, LlaveRepository llaveRepo){
        this.pedidoRepo = pedidoRepo;
        this.llaveRepo = llaveRepo;
    }

    public Pedido crearPedido(Usuario user, Juego juego, Double total){
        // asignar primera llave disponible
        List<Llave> disponibles = llaveRepo.findByJuegoAndVendidaFalse(juego);
        if(disponibles.isEmpty()) throw new RuntimeException("No hay llaves disponibles para " + juego.getTitulo());
        Llave llave = disponibles.get(0);
        llave.setVendida(true);
        llaveRepo.save(llave);

        Pedido p = new Pedido();
        p.setUsuario(user);
        p.setTotal(total);
        p.setCreado(LocalDateTime.now());
        p.setLlaveAsignada(llave);
        return pedidoRepo.save(p);
    }
}
