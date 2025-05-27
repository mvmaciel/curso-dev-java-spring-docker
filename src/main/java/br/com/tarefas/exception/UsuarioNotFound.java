package br.com.tarefas.exception;

import org.springframework.http.HttpStatus;

public class UsuarioNotFound extends RuntimeException implements ApiExceptionContrato {

    private final String code = "USUARIO_NOT_FOUND";
    private String mensagem;

    public UsuarioNotFound(String message) {
        this.mensagem = message;
    }

    @Override
    public String getCode() {
      return this.code;
    }

    @Override
    public String getMensagem() {
      return this.mensagem;
    }

    @Override
    public HttpStatus getHttpStatus() {
      return HttpStatus.NOT_FOUND;
    }
}
