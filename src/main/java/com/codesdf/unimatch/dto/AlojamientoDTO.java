package com.codesdf.unimatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlojamientoDTO {
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
    private Set<String> fotos;
    private Set<String> caracteristicas;
}
