package com.codesdf.unimatch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preferencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Preferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String preferenciaUbicacion;
    private Double presupuestoMax;
    private Boolean compartirHabitacion;
    private Boolean fumador;
    private Boolean mascotasPermitidas;
    private String horasDormirPreferidas;
}