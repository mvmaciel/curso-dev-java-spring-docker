package br.com.tarefas.mapper;

import br.com.tarefas.dto.TarefaDTO;
import br.com.tarefas.entity.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TarefaMapper {
    TarefaDTO toDTO(Tarefa tarefa);
    Tarefa toEntity(TarefaDTO dto);

    List<TarefaDTO> toDTOList(List<Tarefa> tarefas);
    List<Tarefa> toEntityList(List<TarefaDTO> dtos);
}
