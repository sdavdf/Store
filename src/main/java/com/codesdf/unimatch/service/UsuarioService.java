package com.codesdf.unimatch.service;

import com.codesdf.unimatch.dto.PerfilCompletoDTO;
import com.codesdf.unimatch.dto.RegistroDTO;
import com.codesdf.unimatch.dto.UsuarioDTO;
import com.codesdf.unimatch.model.Usuario;
import com.codesdf.unimatch.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final PreferenciaService preferenciaService;
    private final InteresService interesService;

    @Transactional
    public UsuarioDTO registrarUsuario(RegistroDTO registroDTO) {
        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setTelefono(registroDTO.getTelefono());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        usuario.setCarrera(registroDTO.getCarrera());
        usuario.setSemestre(registroDTO.getSemestre());

        // Asignar rol por defecto
        HashSet<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        usuario.setRoles(roles);

        usuario = usuarioRepository.save(usuario);
        return convertirADTO(usuario);
    }

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public PerfilCompletoDTO obtenerPerfilCompleto(Long usuarioId) {
        UsuarioDTO usuarioDTO = obtenerUsuarioPorId(usuarioId);
        return new PerfilCompletoDTO(
                usuarioDTO,
                preferenciaService.obtenerPreferenciaPorUsuarioId(usuarioId),
                interesService.obtenerInteresPorUsuarioId(usuarioId)
        );
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getCarrera(),
                usuario.getSemestre(),
                usuario.getFotoPerfil(),
                usuario.getRoles()
        );
    }
}