package br.com.tarefas.service;

import br.com.tarefas.service.response.TokenResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class KeycloakAuthService {

    private final WebClient webClient;

    public KeycloakAuthService(String keycloakEndpoint) {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .defaultHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
    }

    public Mono<String> obterAccessTokenAdmin() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", "admin-cli");
        formData.add("username", "admin");
        formData.add("password", "admin");
        formData.add("grant_type", "password");

        return webClient.post()
                .uri("/realms/master/protocol/openid-connect/token")
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .map(TokenResponse::getAccess_token);
    }
}

