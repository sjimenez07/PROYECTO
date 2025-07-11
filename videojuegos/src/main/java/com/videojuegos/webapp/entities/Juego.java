/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.videojuegos.webapp.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Juego")
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJuego;
    
    @Column(nullable = false, length = 150)
    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(name = "FechaLanzamiento")
    private LocalDate fechaLanzamiento;
    
    @ManyToOne
    @JoinColumn(name = "IdPlataforma")
    private Plataforma plataforma;
    
    @OneToMany(mappedBy = "juego", cascade = CascadeType.ALL)
    private List<Llave> llaves;
    
    @OneToMany(mappedBy = "juego", cascade = CascadeType.ALL)
    private List<Opinion> opiniones;
    
    // Método para obtener promedio de calificaciones
    public double getPromedioCalificacion() {
        if (opiniones == null || opiniones.isEmpty()) return 0.0;
        return opiniones.stream()
                .mapToInt(Opinion::getCalificacion)
                .average()
                .orElse(0.0);
    }
    
    // Método para verificar disponibilidad
    public boolean isDisponible() {
        return llaves != null && llaves.stream().anyMatch(Llave::isDisponible);
    }
}