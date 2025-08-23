package com.videojuegos.webapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "carrito_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CarritoItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    private Juego juego;

    @Min(value = 1, message = "Debe agregar al menos 1 unidad")
    private Integer cantidad;
}
