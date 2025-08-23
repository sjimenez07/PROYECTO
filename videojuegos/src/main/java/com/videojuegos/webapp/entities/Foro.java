package com.ProyectoTIENDA.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "foros")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Foro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El contenido no puede estar vacío")
    @Lob
    private String contenido;

    @ManyToOne
    private Usuario autor;

    private LocalDateTime creado;

    // Uso de RespuestaForo para mantener coherencia con los repositorios/controlladores previos
    @OneToMany(mappedBy = "foro", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<RespuestaForo> respuestas = new ArrayList<>();
    
    
}
