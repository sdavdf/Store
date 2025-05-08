package com.codesdf.unimatch;

import com.codesdf.unimatch.dto.PerfilCompletoDTO;
import com.codesdf.unimatch.dto.UsuarioDTO;
import com.codesdf.unimatch.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodosLosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @GetMapping("/{id}/perfil")
    public ResponseEntity<PerfilCompletoDTO> obtenerPerfilCompleto(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPerfilCompleto(id));
    }
}
