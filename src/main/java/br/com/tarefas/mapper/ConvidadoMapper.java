package br.com.tarefas.mapper;

import br.com.tarefas.dto.ConvidadoDTO;
import br.com.tarefas.entity.Convidado;
import br.com.tarefas.entity.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConvidadoMapper {

    default ConvidadoDTO toDTO(Convidado convidado) {
        Usuario usuario = convidado.getUsuario();
        return new ConvidadoDTO(usuario.getNome(), usuario.getEmail());
    }

    // Só possível se você tiver o Usuario carregado (senão precisa injetar o repositório)
    default Convidado toEntity(ConvidadoDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        Convidado convidado = new Convidado();
        convidado.setUsuario(usuario);

        return convidado;
    }

    List<ConvidadoDTO> toDTOList(List<Convidado> convidados);
    List<Convidado> toEntityList(List<ConvidadoDTO> dtos);
}
