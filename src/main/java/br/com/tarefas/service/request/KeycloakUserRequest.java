package br.com.tarefas.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakUserRequest {
    private String username;
    private boolean enabled;
    private boolean emailVerified;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> requiredActions;
}

