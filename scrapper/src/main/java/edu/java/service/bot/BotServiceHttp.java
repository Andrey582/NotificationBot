package edu.java.service.bot;

import edu.java.dto.request.BotLinkUpdateRequestDto;
import edu.java.service.BotService;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class BotServiceHttp implements BotService {

    private WebClient webClient;
    private Retry retry;

    public BotServiceHttp(WebClient webClient, Retry retry) {
        this.webClient = webClient;
        this.retry = retry;
    }

    public Mono<Void> sendLinkUpdate(BotLinkUpdateRequestDto linkUpdateRequestDto) {
        return webClient
            .post()
            .uri("/updates")
            .body(Mono.just(linkUpdateRequestDto), BotLinkUpdateRequestDto.class)
            .retrieve()
            .bodyToMono(Void.class)
            .retryWhen(retry);
    }
}
