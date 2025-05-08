package com.codesdf.unimatch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "alojamientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alojamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private Double distancia;
    private Integer habitaciones;
    private String disponibilidad;
    private Double calificacion;
    private String direccion;
    private String latitud;
    private String longitud;

    @ElementCollection
    @CollectionTable(name = "fotos_alojamiento", joinColumns = @JoinColumn(name = "alojamiento_id"))
    @Column(name = "url_foto")
    private Set<String> fotos = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "caracteristicas_alojamiento", joinColumns = @JoinColumn(name = "alojamiento_id"))
    @Column(name = "caracteristica")
    private Set<String> caracteristicas = new HashSet<>();
}
