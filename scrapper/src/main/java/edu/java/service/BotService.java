package edu.java.service;

import edu.java.dto.request.BotLinkUpdateRequestDto;
import edu.java.retry.RetryProvider;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SuppressWarnings("MagicNumber")
@Service
public class BotService {

    @Autowired
    private WebClient botWebClient;

    public Mono<Void> sendLinkUpdate(BotLinkUpdateRequestDto linkUpdateRequestDto) {
        return botWebClient
            .post()
            .uri("/updates")
            .body(Mono.just(linkUpdateRequestDto), BotLinkUpdateRequestDto.class)
            .retrieve()
            .bodyToMono(Void.class)
            .retryWhen(RetryProvider.linearRetry(3L, Duration.ofSeconds(10), Duration.ofSeconds(5), List.of(400, 500)));
    }
}
