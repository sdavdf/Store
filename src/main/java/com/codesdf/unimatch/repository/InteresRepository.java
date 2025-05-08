package com.codesdf.unimatch.repository;

import com.codesdf.unimatch.model.Interes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InteresRepository extends JpaRepository<Interes, Long> {
    Optional<Interes> findByUsuarioId(Long usuarioId);
}
