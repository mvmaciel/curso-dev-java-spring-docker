package br.com.tarefas.controller;

import br.com.tarefas.dto.TarefaDTO;
import br.com.tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefasController {

    @Autowired
    private TarefaService tarefaService;

    @PreAuthorize("hasRole('tarefa')")
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> recuperarTarefa(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.recuperarTarefa(id));
    }

    @PreAuthorize("hasRole('tarefa')")
    @PostMapping
    public ResponseEntity<TarefaDTO> adicionarTarefa(@Valid @RequestBody TarefaDTO tarefa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.adicionarTarefa(tarefa));
    }

    @PreAuthorize("hasRole('tarefa')")
    @GetMapping
    public ResponseEntity<List<TarefaDTO>> recuperaTarefas() {
        return ResponseEntity.ok(tarefaService.recuperaTarefas());
    }

    @PreAuthorize("hasRole('tarefa')")
    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO> atualizaTarefa(@PathVariable Long id, @Valid @RequestBody TarefaDTO tarefa) {
        return ResponseEntity.ok(tarefaService.atualizaTarefa(id, tarefa));
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }
}
