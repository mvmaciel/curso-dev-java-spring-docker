package br.com.tarefas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidateError {
    private String campo;
    private String mensagem;
}
