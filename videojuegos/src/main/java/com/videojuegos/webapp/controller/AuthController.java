package com.videojuegos.controller;

import com.videojuegos.entities.Usuario;
import com.videojuegos.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;

    public AuthController(UsuarioService usuarioService, PasswordEncoder encoder) {
        this.usuarioService = usuarioService;
        this.encoder = encoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model m) {
        m.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario.setRol("ROLE_USER");
        usuarioService.save(usuario);
        return "redirect:/login";
    }

}
