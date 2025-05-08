package com.codesdf.unimatch.repository;

import com.codesdf.unimatch.model.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AlojamientoRepository extends JpaRepository<Alojamiento, Long> {
    @Query("SELECT a FROM Alojamiento a WHERE a.distancia <= :maxDistancia AND a.precio <= :maxPrecio")
    List<Alojamiento> findByDistanciaAndPrecio(Double maxDistancia, Double maxPrecio);
}