package br.com.tarefas.service;

import br.com.tarefas.dto.TarefaDTO;
import br.com.tarefas.dto.UsuarioDTO;
import br.com.tarefas.entity.Convidado;
import br.com.tarefas.entity.Usuario;
import br.com.tarefas.exception.UsuarioNotFound;
import br.com.tarefas.mapper.TarefaMapper;
import br.com.tarefas.mapper.UsuarioMapper;
import br.com.tarefas.repository.ConvidadoRepository;
import br.com.tarefas.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConvidadoRepository convidadoRepository;

    @Autowired
    private TarefaMapper tarefaMapper;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public UsuarioDTO adicionarUsuario(UsuarioDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        return usuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    public List<UsuarioDTO> recuperaUsuarios() {
        return usuarioMapper.toDtoList(usuarioRepository.findAll());
    }

    public List<Usuario> validaConvidadoExistente(List<String> email) {
        List<Usuario> usuariosEncontrados = email.stream()
                .map(usuarioRepository::findByEmail)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        return usuariosEncontrados;
    }

    @Transactional
    public UsuarioDTO atualizaUsuario(Long id, UsuarioDTO atualizado) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNotFound("Usuário não encontrado"));

        usuario.setNome(atualizado.getNome());
        usuario.setTelefone(atualizado.getTelefone());
        usuario.setEmail(atualizado.getEmail());
        return usuarioMapper.toDTO(usuario);
    }

    public List<TarefaDTO> buscaTarefasConvidado(Long usuarioId) {
        List<Convidado> convites = convidadoRepository.findByUsuarioId(usuarioId);
        return convites.stream()
                .map(Convidado::getTarefa)
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
