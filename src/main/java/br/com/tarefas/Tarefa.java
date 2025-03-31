package br.com.tarefas;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Tarefa {

    private Long id;
    private String titulo;
    private String descricao;
    private String local;
    private LocalDateTime dataHora;

}
