package com.codesdf.unimatch.service;

import com.codesdf.unimatch.dto.AlojamientoDTO;
import com.codesdf.unimatch.model.Alojamiento;
import com.codesdf.unimatch.repository.AlojamientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlojamientoService {
    private final AlojamientoRepository alojamientoRepository;

    @Transactional
    public AlojamientoDTO crearAlojamiento(AlojamientoDTO alojamientoDTO) {
        Alojamiento alojamiento = new Alojamiento();
        alojamiento.setNombre(alojamientoDTO.getNombre());
        alojamiento.setDescripcion(alojamientoDTO.getDescripcion());
        alojamiento.setPrecio(alojamientoDTO.getPrecio());
        alojamiento.setDistancia(alojamientoDTO.getDistancia());
        alojamiento.setHabitaciones(alojamientoDTO.getHabitaciones());
        alojamiento.setDisponibilidad(alojamientoDTO.getDisponibilidad());
        alojamiento.setCalificacion(alojamientoDTO.getCalificacion());
        alojamiento.setDireccion(alojamientoDTO.getDireccion());
        alojamiento.setLatitud(alojamientoDTO.getLatitud());
        alojamiento.setLongitud(alojamientoDTO.getLongitud());
        alojamiento.setFotos(alojamientoDTO.getFotos());
        alojamiento.setCaracteristicas(alojamientoDTO.getCaracteristicas());

        alojamiento = alojamientoRepository.save(alojamiento);
        return convertirADTO(alojamiento);
    }

    public List<AlojamientoDTO> buscarAlojamientos(Double maxDistancia, Double maxPrecio) {
        return alojamientoRepository.findByDistanciaAndPrecio(maxDistancia, maxPrecio).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<AlojamientoDTO> obtenerTodosLosAlojamientos() {
        return alojamientoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public AlojamientoDTO obtenerAlojamientoPorId(Long id) {
        return alojamientoRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado"));
    }

    private AlojamientoDTO convertirADTO(Alojamiento alojamiento) {
        return new AlojamientoDTO(
                alojamiento.getId(),
                alojamiento.getNombre(),
                alojamiento.getDescripcion(),
                alojamiento.getPrecio(),
                alojamiento.getDistancia(),
                alojamiento.getHabitaciones(),
                alojamiento.getDisponibilidad(),
                alojamiento.getCalificacion(),
                alojamiento.getDireccion(),
                alojamiento.getLatitud(),
                alojamiento.getLongitud(),
                alojamiento.getFotos(),
                alojamiento.getCaracteristicas()
        );
    }
}