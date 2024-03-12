package edu.java.service;

import edu.java.dto.response.StackOverflowResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SuppressWarnings("MultipleStringLiterals")
@Service
public class StackOverflowIntegrationService {

    @Autowired
    WebClient stackOverflowWebClient;

    public Mono<StackOverflowResponseDto> getSearch(String question) {
        return stackOverflowWebClient
            .get()
            .uri(
                uriBuilder -> uriBuilder
                    .path("/search/advanced")
                    .queryParam("order", "desc")
                    .queryParam("sort", "activity")
                    .queryParam("site", "stackoverflow")
                    .queryParam("q", question)
                    .build()
            ).retrieve()
            .bodyToMono(StackOverflowResponseDto.class);
    }

    public Mono<StackOverflowResponseDto> getQuestionById(Long questionId) {
        return stackOverflowWebClient
            .get()
            .uri(
                uriBuilder -> uriBuilder
                    .path("/questions/" + questionId)
                    .queryParam("order", "desc")
                    .queryParam("sort", "activity")
                    .queryParam("site", "stackoverflow")
                    .build()
            ).retrieve()
            .bodyToMono(StackOverflowResponseDto.class);
    }
}
