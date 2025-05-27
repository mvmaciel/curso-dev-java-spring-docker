package br.com.tarefas.service;

import br.com.tarefas.dto.TarefaDTO;
import br.com.tarefas.entity.Convidado;
import br.com.tarefas.entity.Tarefa;
import br.com.tarefas.entity.Usuario;
import br.com.tarefas.exception.TarefaNotFound;
import br.com.tarefas.mapper.TarefaMapper;
import br.com.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TarefaMapper tarefaMapper;

    public TarefaDTO recuperarTarefa(Long id) {
        Optional<Tarefa> tarefaOp = tarefaRepository.findById(id);

        Tarefa tarefa = tarefaOp.orElseThrow(() -> new TarefaNotFound("Tarefa com o ID " + id + " não encontrado"));
        return tarefaMapper.toDTO(tarefa);
    }

    public TarefaDTO adicionarTarefa(TarefaDTO tarefa) {
        Tarefa tarefaEntity = tarefaMapper.toEntity(tarefa);

        List<Usuario> usuariosConvidados = usuarioService.validaConvidadoExistente(tarefa.getConvidados());
        List<Convidado> convidados = usuariosConvidados.stream()
                .map(usuario -> new Convidado(tarefaEntity, usuario))
                .collect(Collectors.toList());
        tarefaEntity.setConvidados(convidados);

        return tarefaMapper.toDTO(tarefaRepository.save(tarefaEntity));
    }

    public List<TarefaDTO> recuperaTarefas() {
        return tarefaMapper.toDTOList(tarefaRepository.findAll());
    }

    public TarefaDTO atualizaTarefa(Long id, TarefaDTO tarefa) {
        Tarefa tarefaEntity = tarefaMapper.toEntity(tarefa);
        Optional<Tarefa> tarefaOp = tarefaRepository.findById(id);
        if (tarefaOp.isPresent()) {
            tarefaEntity.setId(id);
            return tarefaMapper.toDTO(tarefaRepository.save(tarefaEntity));
        }
        throw new TarefaNotFound("Tarefa com o ID " + id + " não encontrado");
    }

    public void deletarTarefa(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new TarefaNotFound("Tarefa com o ID " + id + " não encontrado");
        }
        tarefaRepository.deleteById(id);
    }
}
