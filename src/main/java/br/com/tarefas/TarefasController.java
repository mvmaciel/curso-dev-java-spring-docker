package br.com.tarefas;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
public class TarefasController {

    private List<Tarefa> listDeTarefas = new ArrayList<>();

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> recuperarTarefa(@PathVariable Long id) {
       Optional<Tarefa> tarefaOp = this.listDeTarefas.stream().filter(tarefa -> tarefa.getId() == id).findFirst();
        return tarefaOp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> adicionarTarefa(@RequestBody Tarefa tarefa){
        boolean existe = this.listDeTarefas.stream().filter(tarefaLista -> tarefaLista.getId() == tarefa.getId()).findFirst().isPresent();
        if(existe) {
            return ResponseEntity.badRequest().body(Map.of("messagem", "Tarefa com o ID " + tarefa.getId() + " já existente na lista"));
        }
        this.listDeTarefas.add(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefa);
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> recuperaTarefas() {
        return ResponseEntity.ok(this.listDeTarefas);
    }
}
