package com.codesdf.unimatch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "intereses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ElementCollection
    @CollectionTable(name = "generos_musicales", joinColumns = @JoinColumn(name = "interes_id"))
    @Column(name = "genero")
    private Set<String> musica = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "deportes", joinColumns = @JoinColumn(name = "interes_id"))
    @Column(name = "deporte")
    private Set<String> deportes = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "hobbies", joinColumns = @JoinColumn(name = "interes_id"))
    @Column(name = "hobby")
    private Set<String> hobbies = new HashSet<>();

    private String estudioPreferencia;
    private String fiestas;
}