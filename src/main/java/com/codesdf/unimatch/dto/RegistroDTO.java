package com.codesdf.unimatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDTO {
    private String nombre;
    private String email;
    private String telefono;
    private String password;
    private String carrera;
    private Integer semestre;
}