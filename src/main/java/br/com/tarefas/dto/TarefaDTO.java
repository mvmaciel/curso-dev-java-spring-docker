package br.com.tarefas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TarefaDTO {

    private Long id;
    /*
    Notblank deve tratar casos de Striing como exemplo:
    Nulo - titulo = null;
    Vázio- titulo = "";
    Em branco - titulo = " ";
     */
    @NotBlank(message = "O título é obrigatório")
    @Size(min = 5, max = 30, message = "O tamanho mínimo de 5 caracteres e máximo de 15 caracteres")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "O local é obrigatório")
    private String local;

    @NotNull(message = "Data e hora são obrigatórios")
    private LocalDateTime dataHora;

    @NotNull(message = "Nenhum convidado foi selecionado")
    private List<ConvidadoDTO> convidados;

    @NotNull
    private UsuarioDTO criador;
}
