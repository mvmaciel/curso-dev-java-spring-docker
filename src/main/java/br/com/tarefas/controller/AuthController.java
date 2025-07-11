package br.com.tarefas.controller;

import br.com.tarefas.dto.ResetPasswordDTO;
import br.com.tarefas.service.KeycloakUserClientService;
import br.com.tarefas.service.UsuarioService;
import br.com.tarefas.service.request.ResetPasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/{userId}")
    public Mono<Void> createUser(@PathVariable String userId, @RequestBody ResetPasswordDTO resetPasswordDTO) {
        return usuarioService.ativarUsuarioPendente(userId, resetPasswordDTO);
    }

}
