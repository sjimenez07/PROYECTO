package com.videojuegos.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/")
    public String inicio() {
        return "index"; // nombre del archivo en templates, sin .html
    }


    @GetMapping("/catalogo")
        public String catalogo() {
            return "catalogo"; // creá catalogo.html después
        }

    @GetMapping("/nosotros")
        public String nosotros() {
            return "nosotros";
        }

    @GetMapping("/contacto")
        public String contacto() {
            return "contacto";
        }

    @GetMapping("/carrito")
        public String carrito() {
            return "carrito";
        }

}