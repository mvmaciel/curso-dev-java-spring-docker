package br.com.tarefas.service;

import br.com.tarefas.dto.*;
import br.com.tarefas.entity.Convidado;
import br.com.tarefas.entity.ConvidadoPendente;
import br.com.tarefas.entity.Usuario;
import br.com.tarefas.exception.UsuarioNotFound;
import br.com.tarefas.mapper.TarefaMapper;
import br.com.tarefas.mapper.UsuarioMapper;
import br.com.tarefas.repository.ConvidadoPendenteRepository;
import br.com.tarefas.repository.ConvidadoRepository;
import br.com.tarefas.repository.UsuarioRepository;
import br.com.tarefas.service.request.KeycloakUserRequest;
import br.com.tarefas.service.request.ResetPasswordRequest;
import br.com.tarefas.util.PasswordGeneratorUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConvidadoRepository convidadoRepository;

    @Autowired
    private ConvidadoPendenteRepository convidadoPendenteRepository;

    @Autowired
    private TarefaMapper tarefaMapper;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private KeycloakUserClientService keycloakUserClientService;


    public UsuarioDTO adicionarUsuario(UsuarioDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        return usuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    public List<UsuarioDTO> recuperaUsuarios() {
        return usuarioMapper.toDtoList(usuarioRepository.findAll());
    }

    public List<Usuario> buscarUsuariosPorEmail(List<ConvidadoDTO> email) {
        return email.stream()
                .map(ConvidadoDTO::getEmail)
                .map(usuarioRepository::findByEmail)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioDTO atualizaUsuario(Long id, UsuarioDTO atualizado) {
        Usuario usuario = buscarOuFalhar(id);

        usuario.setNome(atualizado.getNome());
        usuario.setTelefone(atualizado.getTelefone());
        usuario.setEmail(atualizado.getEmail());
        return usuarioMapper.toDTO(usuario);
    }

    public Mono<Integer> keycloakCriarNovoUsuario(ConvidadoPendente usuarioConvidado) {
        KeycloakUserRequest keycloakUserRequest = getKeycloakUserRequest(usuarioConvidado);

        String senhaRandom = PasswordGeneratorUtil.generate(10);
        System.out.printf("Senha gerada:" + senhaRandom);

        ResetPasswordRequest requestPassword = new ResetPasswordRequest("password", senhaRandom, true);

        return keycloakUserClientService.createUser(keycloakUserRequest)
                .flatMap(userId -> keycloakUserClientService.resetPassword(userId, requestPassword)
                        .then(Mono.fromCallable(() ->
                                convidadoPendenteRepository.updateKeycloakIdByEmail(userId, keycloakUserRequest.getEmail())
                        ))
                );

    }

    private static KeycloakUserRequest getKeycloakUserRequest(ConvidadoPendente usuarioConvidado) {
        KeycloakUserRequest keycloakUserRequest = new KeycloakUserRequest();
        keycloakUserRequest.setUsername(usuarioConvidado.getConvidadoEmail());
        keycloakUserRequest.setEmail(usuarioConvidado.getConvidadoEmail());
        keycloakUserRequest.setEmailVerified(true);
        keycloakUserRequest.setEnabled(true);
        keycloakUserRequest.setFirstName(usuarioConvidado.getConvidadoNome().split(" ")[0]);
        keycloakUserRequest.setLastName(usuarioConvidado.getConvidadoNome().split(" ")[1]);
        keycloakUserRequest.setRequiredActions(List.of("UPDATE_PASSWORD"));
        return keycloakUserRequest;
    }

    private Usuario buscarOuFalhar(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNotFound("Usuário não encontrado"));
    }

    public Mono<Void> ativarUsuarioPendente(String userId, ResetPasswordDTO resetPasswordDTO) {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("password", resetPasswordDTO.getNovaSenha(), false);
        keycloakUserClientService.resetPassword(userId, resetPasswordRequest);
        return keycloakUserClientService.resetPassword(userId, resetPasswordRequest)
                .then(Mono.fromRunnable(() -> {
                    List<ConvidadoPendente> usuariosPendentes = convidadoPendenteRepository.findByKeycloakId(userId);
                    if(!usuariosPendentes.isEmpty()) {
                        criarUsuariosRegistrados(usuariosPendentes);
                    }
                }));
    }

    private void criarUsuariosRegistrados(List<ConvidadoPendente> usuariosPendentes) {
        Usuario usuario = new Usuario();
        usuario.setEmail(usuariosPendentes.get(0).getConvidadoEmail());
        usuario.setNome(usuariosPendentes.get(0).getConvidadoNome());
        usuarioRepository.save(usuario);

        usuariosPendentes.stream()
                .map(convidadoPendente -> new Convidado(convidadoPendente.getTarefa(), usuario))
                .forEach(convidadoRepository::save);

        usuariosPendentes.forEach(convidadoPendenteRepository::delete);
    }

    public List<ConvidadoPendente> buscaUsuariosPendentesNoKeycloak(List<ConvidadoPendente> convidadoPendentes) {
        return convidadoPendentes.stream()
                .flatMap(c -> convidadoPendenteRepository.findConvidadosPendentesKeyCloak(c.getConvidadoEmail()).stream())
                .toList();
    }

    public List<TarefaDTO> buscaTarefasConvidado(Long usuarioId) {
        List<Convidado> convites = convidadoRepository.findByUsuarioId(usuarioId);
        return convites.stream()
                .map(Convidado::getTarefa)
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
