package com.codesdf.unimatch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String telefono;
    private String password;
    private String carrera;
    private Integer semestre;
    private String fotoPerfil;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles_usuarios", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "rol")
    private Set<String> roles = new HashSet<>();

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Preferencia preferencia;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Interes interes;
}