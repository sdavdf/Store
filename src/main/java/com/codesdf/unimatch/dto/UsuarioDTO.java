package com.codesdf.unimatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String carrera;
    private Integer semestre;
    private String fotoPerfil;
    private Set<String> roles;
}