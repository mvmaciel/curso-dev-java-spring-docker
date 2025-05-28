package br.com.tarefas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ConvidadoDTO {
    private String nome;
    private String email;
}
