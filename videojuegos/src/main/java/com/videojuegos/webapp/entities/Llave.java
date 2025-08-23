package com.videojuegos.webapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "llaves")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Llave {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código de la llave es obligatorio")
    @Column(nullable = false, unique = true)
    private String codigo;

    @ManyToOne
    private Juego juego;

    private boolean vendida = false;
}
