package com.codesdf.unimatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InteresDTO {
    private Long id;
    private Set<String> musica;
    private Set<String> deportes;
    private Set<String> hobbies;
    private String estudioPreferencia;
    private String fiestas;
}