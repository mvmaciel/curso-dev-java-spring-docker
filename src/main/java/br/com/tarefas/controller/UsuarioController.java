package br.com.tarefas.controller;

import br.com.tarefas.dto.TarefaDTO;
import br.com.tarefas.dto.UsuarioDTO;
import br.com.tarefas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasRole('usuario')")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> recuperaUsuarios() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.recuperaUsuarios());
    }

    @PreAuthorize("hasRole('usuario')")
    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastraUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.adicionarUsuario(usuarioDTO));
    }

    @PreAuthorize("hasRole('usuario')")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizaUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.atualizaUsuario(id, usuarioDTO));
    }

    @PreAuthorize("hasRole('usuario')")
    @GetMapping("/{usuarioId}/convites")
    public ResponseEntity<List<TarefaDTO>> recuperaConvites(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioService.buscaTarefasConvidado(usuarioId));
    }
}
