package br.com.tarefas.service.request;

public record ResetPasswordRequest(
        String type,
        String value,
        boolean temporary
) {}
