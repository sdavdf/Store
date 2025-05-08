package com.codesdf.unimatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilCompletoDTO {
    private UsuarioDTO usuario;
    private PreferenciaDTO preferencia;
    private InteresDTO interes;
}