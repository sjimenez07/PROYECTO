package com.videojuegos.controller;

import com.videojuegos.service.JuegoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final JuegoService juegoService;
    public HomeController(JuegoService juegoService){ this.juegoService = juegoService; }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("juegos", juegoService.findAll());
        return "index";
    }
}
