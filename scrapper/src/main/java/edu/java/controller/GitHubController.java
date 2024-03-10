package edu.java.controller;

import edu.java.dto.response.GitHubResponseDto;
import edu.java.service.GitHubIntegrationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/github")
public class GitHubController {

    @Autowired
    GitHubIntegrationService gitHubIntegrationService;

    @GetMapping("/{name}/{repo}")
    public Mono<ResponseEntity<List<GitHubResponseDto>>> getEvents(
        @PathVariable String name,
        @PathVariable String repo
    ) {
            return gitHubIntegrationService.getEvents(name, repo);
        }
}
