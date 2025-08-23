package com.videojuegos.controller;

import com.videojuegos.entities.Foro;
import com.videojuegos.entities.RespuestaForo;
import com.videojuegos.entities.Usuario;
import com.videojuegos.repository.UsuarioRepository;
import com.videojuegos.service.ForoService;
import com.videojuegos.service.RespuestaForoService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/foro")
public class ForoController {

    private final ForoService foroService;
    private final RespuestaForoService respuestaForoService;
    private final UsuarioRepository usuarioRepository;

    public ForoController(ForoService foroService, RespuestaForoService respuestaForoService,
            UsuarioRepository usuarioRepository) {
        this.foroService = foroService;
        this.respuestaForoService = respuestaForoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("foros", foroService.listar());
        return "foro/list";
    }

    @GetMapping("/crear")
    public String crearForm(Model model) {
        model.addAttribute("foro", new Foro());
        return "foro/crear";
    }

    @PostMapping("/crear")
    public String crear(@Valid @ModelAttribute Foro foro, BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return "foro/crear";
        }

        // Buscar el usuario completo en la base de datos
        Usuario autor = usuarioRepository.findByCorreo(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        foro.setAutor(autor);
        foro.setCreado(LocalDateTime.now());
        foroService.guardar(foro);

        return "redirect:/foro";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Foro foro = foroService.buscarPorId(id);
        if (foro == null) {
            return "redirect:/foro";
        }
        model.addAttribute("foro", foro);
        model.addAttribute("respuesta", new RespuestaForo());
        return "foro/detail";
    }

    @PostMapping("/{id}/responder")
    public String responder(@PathVariable Long id,
            @ModelAttribute RespuestaForo respuesta,
            @AuthenticationPrincipal UserDetails userDetails) {
        Foro foro = foroService.buscarPorId(id);
        if (foro == null) {
            return "redirect:/foro";
        }

        // Validar que el contenido no esté vacío
        if (respuesta.getContenido() == null || respuesta.getContenido().trim().isEmpty()) {
            return "redirect:/foro/" + id + "?error=contenido_vacio";
        }

        // Buscar el usuario completo en la base de datos
        Usuario autor = usuarioRepository.findByCorreo(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // SOLUCIÓN: Crear nueva instancia para evitar problemas de ID
        RespuestaForo nuevaRespuesta = new RespuestaForo();
        nuevaRespuesta.setContenido(respuesta.getContenido().trim());
        nuevaRespuesta.setForo(foro);
        nuevaRespuesta.setAutor(autor);
        nuevaRespuesta.setCreado(LocalDateTime.now());

        respuestaForoService.guardar(nuevaRespuesta);
        return "redirect:/foro/" + id;

    }
}
