package br.com.tarefas.controller;

import br.com.tarefas.dto.TarefaDTO;
import br.com.tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefasController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> recuperarTarefa(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.recuperarTarefa(id));
    }


    @PostMapping
    public ResponseEntity<TarefaDTO> adicionarTarefa(@Valid @RequestBody TarefaDTO tarefa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.adicionarTarefa(tarefa));
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> recuperaTarefas() {
        return ResponseEntity.ok(tarefaService.recuperaTarefas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO> atualizaTarefa(@PathVariable Long id, @Valid @RequestBody TarefaDTO tarefa) {
        return ResponseEntity.ok(tarefaService.atualizaTarefa(id, tarefa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }
}
