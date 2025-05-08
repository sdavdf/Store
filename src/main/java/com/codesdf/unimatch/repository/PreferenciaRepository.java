package com.codesdf.unimatch.repository;

import com.codesdf.unimatch.model.Preferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PreferenciaRepository extends JpaRepository<Preferencia, Long> {
    Optional<Preferencia> findByUsuarioId(Long usuarioId);
}
