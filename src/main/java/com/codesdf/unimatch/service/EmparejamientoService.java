package com.codesdf.unimatch.service;

import com.codesdf.unimatch.dto.CompaneroDTO;
import com.codesdf.unimatch.model.Interes;
import com.codesdf.unimatch.model.Preferencia;
import com.codesdf.unimatch.model.Usuario;
import com.codesdf.unimatch.repository.InteresRepository;
import com.codesdf.unimatch.repository.PreferenciaRepository;
import com.codesdf.unimatch.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmparejamientoService {
    private final UsuarioRepository usuarioRepository;
    private final PreferenciaRepository preferenciaRepository;
    private final InteresRepository interesRepository;

    public List<CompaneroDTO> encontrarCompanerosCompatibles(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Preferencia preferencia = preferenciaRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Preferencias no encontradas"));

        Interes interes = interesRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Intereses no encontrados"));

        List<Usuario> todosUsuarios = usuarioRepository.findAll().stream()
                .filter(u -> !u.getId().equals(usuarioId))
                .collect(Collectors.toList());

        List<CompaneroDTO> companeros = new ArrayList<>();

        for (Usuario potencialCompanero : todosUsuarios) {
            // Obtener preferencias e intereses del compañero potencial
            Optional<Preferencia> preferenciaCompanero = preferenciaRepository.findByUsuarioId(potencialCompanero.getId());
            Optional<Interes> interesCompanero = interesRepository.findByUsuarioId(potencialCompanero.getId());

            if (preferenciaCompanero.isPresent() && interesCompanero.isPresent()) {
                // Calcular compatibilidad
                double compatibilidad = calcularCompatibilidad(preferencia, interes, preferenciaCompanero.get(), interesCompanero.get());

                // Encontrar intereses comunes
                Set<String> interesesComunes = encontrarInteresesComunes(interes, interesCompanero.get());

                CompaneroDTO companero = new CompaneroDTO(
                        potencialCompanero.getId(),
                        potencialCompanero.getNombre(),
                        potencialCompanero.getFotoPerfil(),
                        potencialCompanero.getCarrera(),
                        potencialCompanero.getSemestre(),
                        compatibilidad,
                        interesesComunes,
                        preferenciaCompanero.get().getPresupuestoMax(),
                        // Simulando una ubicación basada en la preferencia
                        obtenerUbicacionSimulada(preferenciaCompanero.get().getPreferenciaUbicacion())
                );

                companeros.add(companero);
            }
        }

        // Ordenar por compatibilidad descendente
        companeros.sort((c1, c2) -> Double.compare(c2.getCompatibilidad(), c1.getCompatibilidad()));

        return companeros;
    }

    private double calcularCompatibilidad(Preferencia p1, Interes i1, Preferencia p2, Interes i2) {
        double compatibilidad = 0.0;
        double totalPuntos = 0.0;

        // Compatibilidad de presupuesto (20%)
        double difPresupuesto = Math.abs(p1.getPresupuestoMax() - p2.getPresupuestoMax());
        double puntosPorPresupuesto = 20 * (1 - difPresupuesto / Math.max(p1.getPresupuestoMax(), p2.getPresupuestoMax()));
        compatibilidad += puntosPorPresupuesto;
        totalPuntos += 20;

        // Compatibilidad para fumadores (10%)
        if (p1.getFumador() == p2.getFumador()) {
            compatibilidad += 10;
        }
        totalPuntos += 10;

        // Compatibilidad para mascotas (10%)
        if (p1.getMascotasPermitidas() == p2.getMascotasPermitidas()) {
            compatibilidad += 10;
        }
        totalPuntos += 10;

        // Compatibilidad para compartir habitación (10%)
        if (p1.getCompartirHabitacion() == p2.getCompartirHabitacion()) {
            compatibilidad += 10;
        }
        totalPuntos += 10;

        // Compatibilidad para horarios de dormir (10%)
        if (p1.getHorasDormirPreferidas().equals(p2.getHorasDormirPreferidas())) {
            compatibilidad += 10;
        }
        totalPuntos += 10;

        // Intereses musicales (10%)
        double similaridadMusica = calcularSimilaridad(i1.getMusica(), i2.getMusica());
        compatibilidad += similaridadMusica * 10;
        totalPuntos += 10;

        // Deportes (10%)
        double similaridadDeportes = calcularSimilaridad(i1.getDeportes(), i2.getDeportes());
        compatibilidad += similaridadDeportes * 10;
        totalPuntos += 10;

        // Hobbies (10%)
        double similaridadHobbies = calcularSimilaridad(i1.getHobbies(), i2.getHobbies());
        compatibilidad += similaridadHobbies * 10;
        totalPuntos += 10;

        // Compatibilidad para estudio (5%)
        if (i1.getEstudioPreferencia().equals(i2.getEstudioPreferencia())) {
            compatibilidad += 5;
        }
        totalPuntos += 5;

        // Compatibilidad para fiestas (5%)
        if (i1.getFiestas().equals(i2.getFiestas())) {
            compatibilidad += 5;
        }
        totalPuntos += 5;

        // Normalizar el resultado a porcentaje
        return (compatibilidad / totalPuntos) * 100;
    }

    private double calcularSimilaridad(Set<String> set1, Set<String> set2) {
        if (set1.isEmpty() && set2.isEmpty()) {
            return 1.0;
        }

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        Set<String> interseccion = new HashSet<>(set1);
        interseccion.retainAll(set2);

        return (double) interseccion.size() / union.size();
    }

    private Set<String> encontrarInteresesComunes(Interes i1, Interes i2) {
        Set<String> interesesComunes = new HashSet<>();

        // Añadir música común
        Set<String> musicaComun = new HashSet<>(i1.getMusica());
        musicaComun.retainAll(i2.getMusica());
        interesesComunes.addAll(musicaComun);

        // Añadir deportes comunes
        Set<String> deportesComunes = new HashSet<>(i1.getDeportes());
        deportesComunes.retainAll(i2.getDeportes());
        interesesComunes.addAll(deportesComunes);

        // Añadir hobbies comunes
        Set<String> hobbiesComunes = new HashSet<>(i1.getHobbies());
        hobbiesComunes.retainAll(i2.getHobbies());
        interesesComunes.addAll(hobbiesComunes);

        return interesesComunes;
    }

    private String obtenerUbicacionSimulada(String preferenciaUbicacion) {
        Map<String, String> ubicaciones = Map.of(
                "muy_cerca", "0.5km de la universidad",
                "cerca", "1km de la universidad",
                "media", "3km de la universidad",
                "lejos", "5km de la universidad"
        );

        return ubicaciones.getOrDefault(preferenciaUbicacion, "2km de la universidad");
    }
}