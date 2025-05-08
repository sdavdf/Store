package com.codesdf.unimatch.repository;

import com.codesdf.unimatch.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByUsuarioId(Long usuarioId);
    List<Solicitud> findByAlojamientoId(Long alojamientoId);
}
