package br.com.tarefas.mapper;

import br.com.tarefas.dto.TarefaDTO;
import br.com.tarefas.entity.Convidado;
import br.com.tarefas.entity.Tarefa;
import br.com.tarefas.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TarefaMapper {

    @Mapping(target = "convidados", ignore = true)
    TarefaDTO toDTO(Tarefa tarefa);

    @Mapping(target = "convidados", ignore = true)
    Tarefa toEntity(TarefaDTO dto);

    List<TarefaDTO> toDTOList(List<Tarefa> tarefas);

}
