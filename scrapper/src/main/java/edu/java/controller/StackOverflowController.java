package edu.java.controller;

import edu.java.dto.StackOverflowResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SuppressWarnings("MultipleStringLiterals")
@RestController
@RequestMapping("/stackoverflow")
public class StackOverflowController {

    @Autowired
    WebClient stackOverflowWebClient;

    @GetMapping("/search/{question}")
    public Mono<StackOverflowResponseDto> getSearch(@PathVariable String question) {
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

    @GetMapping("/question/{question_id}")
    public Mono<StackOverflowResponseDto> getQuestionById(@PathVariable("question_id") String questionId) {
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
