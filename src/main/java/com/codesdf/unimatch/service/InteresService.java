package com.codesdf.unimatch.service;

import com.codesdf.unimatch.dto.InteresDTO;
import com.codesdf.unimatch.model.Interes;
import com.codesdf.unimatch.model.Usuario;
import com.codesdf.unimatch.repository.InteresRepository;
import com.codesdf.unimatch.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InteresService {
    private final InteresRepository interesRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public InteresDTO guardarInteres(Long usuarioId, InteresDTO interesDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Interes interes = interesRepository.findByUsuarioId(usuarioId)
                .orElse(new Interes());

        interes.setUsuario(usuario);
        interes.setMusica(interesDTO.getMusica());
        interes.setDeportes(interesDTO.getDeportes());
        interes.setHobbies(interesDTO.getHobbies());
        interes.setEstudioPreferencia(interesDTO.getEstudioPreferencia());
        interes.setFiestas(interesDTO.getFiestas());

        interes = interesRepository.save(interes);
        return convertirADTO(interes);
    }

    public InteresDTO obtenerInteresPorUsuarioId(Long usuarioId) {
        return interesRepository.findByUsuarioId(usuarioId)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Intereses no encontrados"));
    }

    private InteresDTO convertirADTO(Interes interes) {
        return new InteresDTO(
                interes.getId(),
                interes.getMusica(),
                interes.getDeportes(),
                interes.getHobbies(),
                interes.getEstudioPreferencia(),
                interes.getFiestas()
        );
    }
}