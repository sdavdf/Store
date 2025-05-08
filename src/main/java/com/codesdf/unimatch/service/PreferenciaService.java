package com.codesdf.unimatch.service;

import com.codesdf.unimatch.dto.PreferenciaDTO;
import com.codesdf.unimatch.model.Preferencia;
import com.codesdf.unimatch.model.Usuario;
import com.codesdf.unimatch.repository.PreferenciaRepository;
import com.codesdf.unimatch.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferenciaService {
    private final PreferenciaRepository preferenciaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public PreferenciaDTO guardarPreferencia(Long usuarioId, PreferenciaDTO preferenciaDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Preferencia preferencia = preferenciaRepository.findByUsuarioId(usuarioId)
                .orElse(new Preferencia());

        preferencia.setUsuario(usuario);
        preferencia.setPreferenciaUbicacion(preferenciaDTO.getPreferenciaUbicacion());
        preferencia.setPresupuestoMax(preferenciaDTO.getPresupuestoMax());
        preferencia.setCompartirHabitacion(preferenciaDTO.getCompartirHabitacion());
        preferencia.setFumador(preferenciaDTO.getFumador());
        preferencia.setMascotasPermitidas(preferenciaDTO.getMascotasPermitidas());
        preferencia.setHorasDormirPreferidas(preferenciaDTO.getHorasDormirPreferidas());

        preferencia = preferenciaRepository.save(preferencia);
        return convertirADTO(preferencia);
    }

    public PreferenciaDTO obtenerPreferenciaPorUsuarioId(Long usuarioId) {
        return preferenciaRepository.findByUsuarioId(usuarioId)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Preferencias no encontradas"));
    }

    private PreferenciaDTO convertirADTO(Preferencia preferencia) {
        return new PreferenciaDTO(
                preferencia.getId(),
                preferencia.getPreferenciaUbicacion(),
                preferencia.getPresupuestoMax(),
                preferencia.getCompartirHabitacion(),
                preferencia.getFumador(),
                preferencia.getMascotasPermitidas(),
                preferencia.getHorasDormirPreferidas()
        );
    }
}