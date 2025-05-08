package com.codesdf.unimatch.service;

import com.codesdf.unimatch.dto.SolicitudDTO;
import com.codesdf.unimatch.model.Alojamiento;
import com.codesdf.unimatch.model.Solicitud;
import com.codesdf.unimatch.model.Usuario;
import com.codesdf.unimatch.repository.AlojamientoRepository;
import com.codesdf.unimatch.repository.SolicitudRepository;
import com.codesdf.unimatch.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolicitudService {
    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlojamientoRepository alojamientoRepository;

    @Transactional
    public SolicitudDTO crearSolicitud(Long usuarioId, Long alojamientoId, String comentarios) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado"));

        Solicitud solicitud = new Solicitud();
        solicitud.setUsuario(usuario);
        solicitud.setAlojamiento(alojamiento);
        solicitud.setFechaSolicitud(LocalDateTime.now());
        solicitud.setEstado("PENDIENTE");
        solicitud.setComentarios(comentarios);

        solicitud = solicitudRepository.save(solicitud);
        return convertirADTO(solicitud);
    }

    public List<SolicitudDTO> obtenerSolicitudesPorUsuario(Long usuarioId) {
        return solicitudRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<SolicitudDTO> obtenerSolicitudesPorAlojamiento(Long alojamientoId) {
        return solicitudRepository.findByAlojamientoId(alojamientoId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SolicitudDTO actualizarEstadoSolicitud(Long solicitudId, String nuevoEstado) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(nuevoEstado);
        solicitud = solicitudRepository.save(solicitud);
        return convertirADTO(solicitud);
    }

    private SolicitudDTO convertirADTO(Solicitud solicitud) {
        return new SolicitudDTO(
                solicitud.getId(),
                solicitud.getUsuario().getId(),
                solicitud.getUsuario().getNombre(),
                solicitud.getAlojamiento().getId(),
                solicitud.getAlojamiento().getNombre(),
                solicitud.getFechaSolicitud(),
                solicitud.getEstado(),
                solicitud.getComentarios()
        );
    }
}