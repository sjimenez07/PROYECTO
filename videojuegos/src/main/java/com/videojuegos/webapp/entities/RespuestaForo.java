package com.videojuegos.webapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas_foro")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RespuestaForo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La respuesta no puede estar vacía")
    @Lob
    private String contenido;

    @ManyToOne
    private Usuario autor;

    @ManyToOne
    private Foro foro;

    private LocalDateTime creado;
}
