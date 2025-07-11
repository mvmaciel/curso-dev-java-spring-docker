package br.com.tarefas.service;

import br.com.tarefas.service.request.KeycloakUserRequest;
import br.com.tarefas.service.request.ResetPasswordRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class KeycloakUserClientService {

    private final WebClient webClient;
    private final KeycloakAuthService authService;


    public KeycloakUserClientService(@Value("${spring.keycloak.endpoint}") String keycloakEndpoint) {
        this.webClient = WebClient.builder()
                .baseUrl(keycloakEndpoint)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.authService = new KeycloakAuthService(keycloakEndpoint);
    }

    public Mono<String> createUser(KeycloakUserRequest keycloakUserRequest) {
        return authService.obterAccessTokenAdmin()
                .flatMap(token -> webClient.post()
                        .uri("/admin/realms/agenda-tarefas/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .bodyValue(keycloakUserRequest)
                        .retrieve()
                        .toBodilessEntity()
                        .map(response -> {
                            URI location = response.getHeaders().getLocation();
                            if (location != null) {
                                String path = location.getPath();
                                return path.substring(path.lastIndexOf('/') + 1);
                            } else {
                                throw new RuntimeException("Header 'Location' não encontrado na resposta.");
                            }
                        }));
    }

    public Mono<Void> resetPassword(String userId, ResetPasswordRequest request) {
        return authService.obterAccessTokenAdmin()
                .flatMap(token -> webClient.put()
                        .uri("/admin/realms/agenda-tarefas/users/{id}/reset-password", userId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .bodyValue(request)
                        .retrieve()
                        .toBodilessEntity()
                        .then());
    }


}
