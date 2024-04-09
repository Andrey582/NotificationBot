package edu.java.service;

import edu.java.dto.response.GitHubResponseDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class GitHubIntegrationService {

    @Autowired
    private WebClient gitHubWebClient;

    @Autowired
    private Retry retry;

    public Mono<ResponseEntity<List<GitHubResponseDto>>> getEvents(String name, String repo) {
        return gitHubWebClient
            .get()
            .uri(
                uriBuilder -> uriBuilder
                    .path(String.format("/repos/" + name + "/" + repo + "/events"))
                    .build()
            ).retrieve()
            .toEntityList(GitHubResponseDto.class)
            .retryWhen(retry);
    }
}
