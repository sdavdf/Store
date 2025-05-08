package com.codesdf.unimatch.contoller;

import com.codesdf.unimatch.dto.LoginDTO;
import com.codesdf.unimatch.dto.RegistroDTO;
import com.codesdf.unimatch.dto.UsuarioDTO;
import com.codesdf.unimatch.security.JwtTokenProvider;
import com.codesdf.unimatch.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar token JWT
        String token = jwtTokenProvider.generarToken(authentication);

        // Obtener usuario logueado
        UsuarioDTO usuario = usuarioService.buscarPorEmail(loginDTO.getEmail())
                .map(u -> new UsuarioDTO(
                        u.getId(),
                        u.getNombre(),
                        u.getEmail(),
                        u.getTelefono(),
                        u.getCarrera(),
                        u.getSemestre(),
                        u.getFotoPerfil(),
                        u.getRoles()
                ))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("usuario", usuario);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registro(@RequestBody RegistroDTO registroDTO) {
        UsuarioDTO nuevoUsuario = usuarioService.registrarUsuario(registroDTO);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }
}