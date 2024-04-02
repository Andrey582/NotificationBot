package edu.java.service;

import edu.java.dto.response.StackOverflowResponseDto;
import edu.java.retry.RetryProvider;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SuppressWarnings({"MultipleStringLiterals", "MagicNumber"})
@Service
public class StackOverflowIntegrationService {

    @Autowired
    private WebClient stackOverflowWebClient;

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
            .bodyToMono(StackOverflowResponseDto.class)
            .retryWhen(RetryProvider.exponentialRetry(3L, Duration.ofSeconds(10), List.of(400, 500)));
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
            .bodyToMono(StackOverflowResponseDto.class)
            .retryWhen(RetryProvider.exponentialRetry(3L, Duration.ofSeconds(10), List.of(400, 500)));
    }
}
