package com.videojuegos.controller;

import com.videojuegos.entities.*;
import com.videojuegos.repository.UsuarioRepository;
import com.videojuegos.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
    private final CarritoService carritoService;
    private final JuegoService juegoService;
    private final UsuarioRepository usuarioRepo;
    private final PedidoService pedidoService;
    private final LlaveService llaveService;

    public CarritoController(CarritoService carritoService, JuegoService juegoService,
                             UsuarioRepository usuarioRepo, PedidoService pedidoService,
                             LlaveService llaveService){
        this.carritoService = carritoService; 
        this.juegoService = juegoService;
        this.usuarioRepo = usuarioRepo; 
        this.pedidoService = pedidoService;
        this.llaveService = llaveService;
    }

    @GetMapping
    public String verCarrito(Model m, @AuthenticationPrincipal User user){
        Usuario u = usuarioRepo.findByCorreo(user.getUsername()).orElseThrow();
        List<CarritoItem> items = carritoService.getCarrito(u);
        
        // Calcular total
        double total = items.stream()
                .mapToDouble(ci -> ci.getJuego().getPrecio() * ci.getCantidad())
                .sum();
        
        m.addAttribute("items", items);
        m.addAttribute("totalCarrito", total);
        return "carrito";
    }

    @PostMapping("/agregar/{juegoId}")
    public String agregar(@PathVariable Long juegoId, @AuthenticationPrincipal User user,
                          RedirectAttributes redirectAttributes){
        try {
            Usuario u = usuarioRepo.findByCorreo(user.getUsername()).orElseThrow();
            Optional<Juego> optJuego = juegoService.findById(juegoId);
            
            if (optJuego.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Juego no encontrado");
                return "redirect:/";
            }
            
            Juego juego = optJuego.get();
            
            // Verificar stock disponible
            List<Llave> llavesDisponibles = llaveService.disponiblesParaJuego(juego);
            if (llavesDisponibles.isEmpty() || juego.getStock() <= 0) {
                redirectAttributes.addFlashAttribute("error", 
                    "No hay stock disponible para " + juego.getTitulo());
                return "redirect:/juegos/" + juegoId;
            }
            
            carritoService.addToCarrito(u, juego, 1);
            redirectAttributes.addFlashAttribute("success", 
                juego.getTitulo() + " agregado al carrito");
                
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al agregar al carrito: " + e.getMessage());
        }
        
        return "redirect:/carrito";
    }

    @GetMapping("/eliminar/{juegoId}")
    public String eliminar(@PathVariable Long juegoId, @AuthenticationPrincipal User user,
                           RedirectAttributes redirectAttributes){
        try {
            Usuario u = usuarioRepo.findByCorreo(user.getUsername()).orElseThrow();
            Optional<Juego> optJuego = juegoService.findById(juegoId);
            
            if (optJuego.isPresent()) {
                carritoService.removeFromCarrito(u, optJuego.get());
                redirectAttributes.addFlashAttribute("success", 
                    optJuego.get().getTitulo() + " eliminado del carrito");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al eliminar del carrito: " + e.getMessage());
        }
        
        return "redirect:/carrito";
    }

    @PostMapping("/checkout")
    public String checkout(@AuthenticationPrincipal User user, Model m, 
                           RedirectAttributes redirectAttributes){
        try {
            Usuario u = usuarioRepo.findByCorreo(user.getUsername()).orElseThrow();
            List<CarritoItem> items = carritoService.getCarrito(u);
            
            if (items.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El carrito está vacío");
                return "redirect:/carrito";
            }
            
            // Verificar stock antes de procesar
            for (CarritoItem item : items) {
                List<Llave> llavesDisponibles = llaveService.disponiblesParaJuego(item.getJuego());
                if (llavesDisponibles.size() < item.getCantidad()) {
                    redirectAttributes.addFlashAttribute("error", 
                        "Stock insuficiente para " + item.getJuego().getTitulo() + 
                        ". Disponibles: " + llavesDisponibles.size() + 
                        ", Solicitadas: " + item.getCantidad());
                    return "redirect:/carrito";
                }
            }
            
            double total = items.stream()
                    .mapToDouble(ci -> ci.getJuego().getPrecio() * ci.getCantidad())
                    .sum();
            
            // Procesar cada item del carrito
            for (CarritoItem item : items) {
                for (int i = 0; i < item.getCantidad(); i++) {
                    pedidoService.crearPedido(u, item.getJuego(), item.getJuego().getPrecio());
                }
            }
            
            // Limpiar carrito después de compra exitosa
            carritoService.clear(u);
            
            // Mostrar página de confirmación
            m.addAttribute("compraExitosa", true);
            m.addAttribute("total", total);
            m.addAttribute("items", items); // Para mostrar lo que se compró
            
            return "carrito";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al procesar la compra: " + e.getMessage());
            return "redirect:/carrito";
        }
    }
}