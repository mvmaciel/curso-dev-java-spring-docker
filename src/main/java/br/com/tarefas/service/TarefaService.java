package br.com.tarefas.service;

import br.com.tarefas.dto.TarefaDTO;
import br.com.tarefas.entity.Convidado;
import br.com.tarefas.entity.ConvidadoPendente;
import br.com.tarefas.entity.Tarefa;
import br.com.tarefas.entity.Usuario;
import br.com.tarefas.exception.TarefaNotFound;
import br.com.tarefas.mapper.TarefaMapper;
import br.com.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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
        Tarefa tarefa = buscarOuFalhar(id);
        return tarefaMapper.toDTO(tarefa);
    }

    private Tarefa buscarOuFalhar(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNotFound("Tarefa com o ID " + id + " não encontrado"));
    }

    public TarefaDTO adicionarTarefa(TarefaDTO tarefa) {
        Tarefa tarefaEntity = tarefaMapper.toEntity(tarefa);

        atribuirConvidados(tarefa, tarefaEntity);

        TarefaDTO tarefaDTO = tarefaMapper.toDTO(tarefaRepository.save(tarefaEntity));

        if (!tarefaDTO.getConvidadoPendente().isEmpty()) {
            List<Mono<Integer>> usuariosMonos = tarefaEntity.getConvidadoPendente().stream()
                    .filter(c -> c.getKeycloakId() == null || c.getKeycloakId().isBlank())
                    .map(usuarioService::keycloakCriarNovoUsuario)
                    .toList();

            Integer usuariosCriados = Flux.merge(usuariosMonos)
                    .reduce(0, Integer::sum).block();
            System.out.println("Total de usuários criados no keycloak: " + usuariosCriados);
        }
        return tarefaDTO;
    }

    private void atribuirConvidadosValidos(TarefaDTO tarefa, Tarefa tarefaEntity) {
        List<Usuario> usuariosConvidados = usuarioService.buscarUsuariosPorEmail(tarefa.getConvidados());
        List<Convidado> convidados = usuariosConvidados.stream()
                .map(usuario -> new Convidado(tarefaEntity, usuario))
                .collect(Collectors.toList());
        tarefaEntity.setConvidados(convidados);
    }

    private void atribuirConvidadosPendentes(TarefaDTO tarefa, Tarefa tarefaEntity) {
        List<String> emailsConvidadosExistentes = tarefaEntity.getConvidados().stream()
                .map(convidado -> convidado.getUsuario().getEmail())
                .toList();

        List<ConvidadoPendente> convidadoPendentes = tarefa.getConvidados().stream()
                .filter(dto -> !emailsConvidadosExistentes.contains(dto.getEmail()))
                .map(dto -> new ConvidadoPendente(tarefaEntity, dto.getNome(), dto.getEmail()))
                .toList();

        List<ConvidadoPendente> usuarioKeycloakPendente = usuarioService.buscaUsuariosPendentesNoKeycloak(convidadoPendentes);

        Map<String, ConvidadoPendente> mapUsuarioKeycloak = usuarioKeycloakPendente.stream()
                .collect(Collectors.toMap(ConvidadoPendente::getConvidadoEmail, Function.identity()));

        convidadoPendentes.forEach(convidado -> {
            ConvidadoPendente keycloakPendente = mapUsuarioKeycloak.get(convidado.getConvidadoEmail());
            if (keycloakPendente != null) {
                convidado.setKeycloakId(keycloakPendente.getKeycloakId());
            }
        });

        tarefaEntity.setConvidadoPendente(convidadoPendentes);
    }

    public List<TarefaDTO> recuperaTarefas() {
        return tarefaMapper.toDTOList(tarefaRepository.findAll());
    }

    public TarefaDTO atualizaTarefa(Long id, TarefaDTO tarefa) {
        Tarefa tarefaEntity = tarefaMapper.toEntity(tarefa);
        Optional<Tarefa> tarefaOp = tarefaRepository.findById(id);
        if (tarefaOp.isPresent()) {
            tarefaEntity.setId(id);
            atribuirConvidados(tarefa, tarefaEntity);
            return tarefaMapper.toDTO(tarefaRepository.save(tarefaEntity));
        }
        throw new TarefaNotFound("Tarefa com o ID " + id + " não encontrado");
    }

    private void atribuirConvidados(TarefaDTO tarefa, Tarefa tarefaEntity) {
        atribuirConvidadosValidos(tarefa, tarefaEntity);
        atribuirConvidadosPendentes(tarefa, tarefaEntity);
    }

    public void deletarTarefa(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new TarefaNotFound("Tarefa com o ID " + id + " não encontrado");
        }
        tarefaRepository.deleteById(id);
    }
}