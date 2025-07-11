package br.com.tarefas.mapper;

import br.com.tarefas.dto.ConvidadoPendenteDTO;
import br.com.tarefas.entity.ConvidadoPendente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConvidadoPendenteMapper {
    default ConvidadoPendenteDTO toDTO(ConvidadoPendente convidadoPendente) {
        return new ConvidadoPendenteDTO(convidadoPendente.getConvidadoNome(), convidadoPendente.getConvidadoEmail());
    }

    default ConvidadoPendente toEntity(ConvidadoPendenteDTO dto) {
        ConvidadoPendente convidadoPendente = new ConvidadoPendente();
        convidadoPendente.setConvidadoNome(dto.getConvidadoNome());
        convidadoPendente.setConvidadoEmail(dto.getConvidadoEmail());
        return convidadoPendente;
    }
}
