package br.com.tarefas.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeycloakTokenRequest {
    private String client_id;
    private String username;
    private String password;
    private String grant_type;
}

