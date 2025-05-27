package br.com.tarefas.exception;

import org.springframework.http.HttpStatus;

public class TarefaNotFound extends RuntimeException implements ApiExceptionContrato {

    private final String code = "TAREFA_NOT_FOUND";
    private String mensagem;

    public TarefaNotFound(String mensagem){
        this.mensagem = mensagem;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMensagem() {
        return mensagem;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
