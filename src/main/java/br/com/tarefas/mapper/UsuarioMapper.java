package br.com.tarefas.mapper;


import br.com.tarefas.dto.UsuarioDTO;
import br.com.tarefas.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    UsuarioDTO toDTO(Usuario usuario);

    Usuario toEntity(UsuarioDTO dto);

    List<UsuarioDTO> toDtoList(List<Usuario> entities);
}
