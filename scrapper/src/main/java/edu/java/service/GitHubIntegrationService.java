package edu.java.service;

import edu.java.dto.response.GitHubResponseDto;
import edu.java.retry.RetryProvider;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SuppressWarnings("MagicNumber")
@Service
public class GitHubIntegrationService {

    @Autowired
    private WebClient gitHubWebClient;

    public Mono<ResponseEntity<List<GitHubResponseDto>>> getEvents(String name, String repo) {
        return gitHubWebClient
            .get()
            .uri(
                uriBuilder -> uriBuilder
                    .path(String.format("/repos/" + name + "/" + repo + "/events"))
                    .build()
            ).retrieve()
            .toEntityList(GitHubResponseDto.class)
            .retryWhen(RetryProvider.exponentialRetry(3L, Duration.ofSeconds(10), List.of(400, 500)));
    }
}
