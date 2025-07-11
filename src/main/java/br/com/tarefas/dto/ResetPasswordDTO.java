package br.com.tarefas.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String senhaAtual;
    private String novaSenha;
}
