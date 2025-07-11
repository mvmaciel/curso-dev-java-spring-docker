package br.com.tarefas.mapper;

import br.com.tarefas.dto.ConvidadoDTO;
import br.com.tarefas.dto.TarefaDTO;
import br.com.tarefas.entity.Convidado;
import br.com.tarefas.entity.Tarefa;
import br.com.tarefas.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = { ConvidadoMapper.class, ConvidadoPendenteMapper.class }, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TarefaMapper {

    @Mapping(target = "convidados", source = "convidados")
    @Mapping(target = "convidadoPendente", source = "convidadoPendente")
    TarefaDTO toDTO(Tarefa tarefa);

    Tarefa toEntity(TarefaDTO dto);

    List<TarefaDTO> toDTOList(List<Tarefa> tarefas);

}
