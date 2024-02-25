package edu.java.controller;

import edu.java.dto.GitHubResponseDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/github")
public class GitHubController {

    @Autowired
    WebClient gitHubWebClient;

    @GetMapping("/{name}/{repo}")
    public Mono<ResponseEntity<List<GitHubResponseDto>>> getEvents(
        @PathVariable String name,
        @PathVariable String repo
    ) {
        return gitHubWebClient
            .get()
            .uri(
                uriBuilder -> uriBuilder
                    .path(String.format("/repos/" + name + "/" + repo + "/events"))
                    .build()
            ).retrieve()
            .toEntityList(GitHubResponseDto.class);
    }
}
