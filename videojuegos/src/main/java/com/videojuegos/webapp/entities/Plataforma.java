package com.videojuegos.webapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "plataformas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Plataforma {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la plataforma es obligatorio")
    private String nombre;
}
