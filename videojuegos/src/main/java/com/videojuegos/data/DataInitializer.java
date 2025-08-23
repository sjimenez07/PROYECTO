package com.videojuegos.data;

import com.videojuegos.entities.*;
import com.videojuegos.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepo;
    private final PlataformaRepository plataformaRepo;
    private final JuegoRepository juegoRepo;
    private final LlaveRepository llaveRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepo, PlataformaRepository plataformaRepo,
                           JuegoRepository juegoRepo, LlaveRepository llaveRepo, PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.plataformaRepo = plataformaRepo;
        this.juegoRepo = juegoRepo;
        this.llaveRepo = llaveRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeUsers();
        initializePlatforms();
        initializeGames();
        System.out.println("=== DATOS DE PRUEBA INICIALIZADOS ===");
    }

    private void initializeUsers() {
        // Crear usuario admin si no existe
        if(usuarioRepo.findByCorreo("admin@tienda.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin");
            admin.setApellido("Principal");
            admin.setCorreo("admin@tienda.com");
            admin.setPassword(passwordEncoder.encode("Admin123!"));
            admin.setRol("ROLE_ADMIN");
            usuarioRepo.save(admin);
            System.out.println("✅ Admin creado -> admin@tienda.com / Admin123!");
        }

        // Crear usuario de prueba si no existe
        if(usuarioRepo.findByCorreo("user@test.com").isEmpty()) {
            Usuario user = new Usuario();
            user.setNombre("Usuario");
            user.setApellido("Prueba");
            user.setCorreo("user@test.com");
            user.setPassword(passwordEncoder.encode("User123!"));
            user.setRol("ROLE_USER");
            usuarioRepo.save(user);
            System.out.println("✅ Usuario de prueba creado -> user@test.com / User123!");
        }
    }

    private void initializePlatforms() {
        // Crear plataformas si no existen
        if(plataformaRepo.count() == 0) {
            String[] plataformas = {"Steam", "Epic Games Store", "Origin", "Ubisoft Connect", "GOG"};
            
            for(String nombre : plataformas) {
                Plataforma plataforma = new Plataforma();
                plataforma.setNombre(nombre);
                plataformaRepo.save(plataforma);
            }
            System.out.println("✅ Plataformas creadas: Steam, Epic Games Store, Origin, Ubisoft Connect, GOG");
        }
    }

    private void initializeGames() {
        // Crear juegos de ejemplo si no existen
        if(juegoRepo.count() == 0) {
            List<Plataforma> plataformas = plataformaRepo.findAll();
            
            // Datos de juegos de ejemplo
            String[][] juegosDatos = {
                {"Aventuras Épicas", "Un RPG de mundo abierto lleno de aventuras épicas y personajes memorables. Explora vastos territorios, completa misiones épicas y forja tu destino.", "29.99", "5"},
                {"Carreras Turbo", "Simulador de carreras ultrarrápidas con gráficos de última generación. Compite en pistas espectaculares con los mejores autos del mundo.", "19.99", "8"},
                {"Guerra Espacial", "Combates épicos en el espacio con naves personalizables. Conquista galaxias y construye tu imperio espacial.", "24.99", "3"},
                {"Mundo Fantástico", "Aventura mágica en un mundo lleno de criaturas místicas y hechizos poderosos.", "34.99", "7"},
                {"Supervivencia Extrema", "Sobrevive en un mundo postapocalíptico lleno de peligros y recursos escasos.", "22.50", "4"}
            };

            for(int i = 0; i < juegosDatos.length && i < plataformas.size(); i++) {
                Juego juego = new Juego();
                juego.setTitulo(juegosDatos[i][0]);
                juego.setDescripcion(juegosDatos[i][1]);
                juego.setPrecio(Double.parseDouble(juegosDatos[i][2]));
                juego.setStock(Integer.parseInt(juegosDatos[i][3]));
                juego.setPlataforma(plataformas.get(i % plataformas.size()));
                
                // Intentar cargar imagen de ejemplo (opcional)
                try {
                    Path imagePath = Path.of("src/main/resources/static/images/game-placeholder.png");
                    if(Files.exists(imagePath)) {
                        juego.setImagen(Files.readAllBytes(imagePath));
                    }
                } catch (Exception e) {
                    System.out.println("ℹ️  No se pudo cargar imagen para " + juego.getTitulo() + " (esto es normal)");
                }
                
                Juego juegoGuardado = juegoRepo.save(juego);
                generarLlavesParaJuego(juegoGuardado, juego.getStock());
            }

            System.out.println("✅ Juegos de ejemplo creados con sus respectivas llaves.");
        }
    }

    private void generarLlavesParaJuego(Juego juego, int cantidad) {
        for(int i = 1; i <= cantidad; i++){
            Llave llave = new Llave();
            String codigo = generarCodigoUnico(juego, i);
            llave.setCodigo(codigo);
            llave.setJuego(juego);
            llave.setVendida(false);
            llaveRepo.save(llave);
        }
        System.out.println("   🔑 Generadas " + cantidad + " llaves para: " + juego.getTitulo());
    }

    private String generarCodigoUnico(Juego juego, int numero) {
        // Limpiar título para el código
        String tituloLimpio = juego.getTitulo()
                .replaceAll("[^A-Za-z0-9]", "")
                .toUpperCase();
        
        if (tituloLimpio.length() > 8) {
            tituloLimpio = tituloLimpio.substring(0, 8);
        }
        
        // Generar código único con timestamp
        long timestamp = System.currentTimeMillis();
        String timestampStr = String.valueOf(timestamp).substring(8);
        
        return "KEY-" + tituloLimpio + "-" + timestampStr + "-" + String.format("%03d", numero);
    }
}