package com.videojuegos.webapp.controller;

import com.videojuegos.entities.Juego;
import com.videojuegos.service.JuegoService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/juegos")
public class JuegoController {
    private final JuegoService juegoService;
    public JuegoController(JuegoService juegoService){ this.juegoService = juegoService; }

    @GetMapping
    public String list(Model m){ m.addAttribute("juegos", juegoService.findAll()); return "juegos/list"; }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model m){
        Optional<Juego> oj = juegoService.findById(id);
        if(oj.isEmpty()) return "redirect:/juegos";
        m.addAttribute("juego", oj.get());
        return "juegos/detail";
    }

    @GetMapping("/imagen/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> imagen(@PathVariable Long id){
        Optional<Juego> oj = juegoService.findById(id);
        if(oj.isPresent() && oj.get().getImagen()!=null){
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(oj.get().getImagen());
        }
        return ResponseEntity.notFound().build();
    }
}
